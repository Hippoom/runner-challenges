package com.github.hippoom.runner.challenges.http.assembler;

import com.github.hippoom.runner.challenges.domain.model.challenge.Challenge;
import com.github.hippoom.runner.challenges.domain.model.challenge.CompletedChallenge;
import com.github.hippoom.runner.challenges.domain.model.challenge.CompletedChallengeRepository;
import com.github.hippoom.runner.challenges.domain.model.challenge.StartedChallenge;
import com.github.hippoom.runner.challenges.domain.model.challenge.StartedChallengeRepository;
import com.github.hippoom.runner.challenges.domain.model.user.UserId;
import com.github.hippoom.runner.challenges.http.representation.MyChallengeRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MyChallengeRepresentationAssembler
        implements RepresentationModelAssembler<Challenge, MyChallengeRepresentation> {

    private final CompletedChallengeRepository completedChallengeRepository;
    private final StartedChallengeRepository startedChallengeRepository;

    /**
     * Optimized method to convert multiple challenges, avoiding N+1 queries
     * by loading all completed challenges for the user at once.
     */
    public List<MyChallengeRepresentation> toModels(List<Challenge> challenges, String userId) {
        // Phase 1: Create basic representations
        List<MyChallengeRepresentation> representations = createBasicRepresentations(challenges);
        
        // Phase 2: Populate status fields
        populateStatusFields(representations, userId);
        
        return representations;
    }

    @Override
    public MyChallengeRepresentation toModel(Challenge challenge) {
        // This method is required by RepresentationModelAssembler interface
        // but should not be used as it would cause N+1 queries
        throw new UnsupportedOperationException("Use toModels(List<Challenge>, String) instead to avoid N+1 queries");
    }
    
    public MyChallengeRepresentation toModel(StartedChallenge startedChallenge) {
        MyChallengeRepresentation repr = new MyChallengeRepresentation();
        repr.setNumber(startedChallenge.getNumber().getValue());
        repr.setCompleted(false);
        repr.setAvailable(true);
        repr.setStarted(true);

        // TODO: Add HATEOAS links
        return repr;
    }

    /**
     * Phase 1: Create basic representations with only core fields
     */
    private List<MyChallengeRepresentation> createBasicRepresentations(List<Challenge> challenges) {
        return challenges.stream()
                .map(challenge -> {
                    MyChallengeRepresentation repr = new MyChallengeRepresentation();
                    repr.setNumber(challenge.getNumber().getValue());
                    return repr;
                })
                .collect(Collectors.toList());
    }

    /**
     * Phase 2: Populate status fields using batch-loaded data
     */
    private void populateStatusFields(List<MyChallengeRepresentation> representations, String userId) {
        UserId userIdObj = new UserId(userId);
        
        // Load completed challenges for user in one query
        List<CompletedChallenge> completedChallenges = completedChallengeRepository
                .findByUserId(userIdObj);
        
        Set<Integer> completedNumbers = completedChallenges.stream()
                .map(completed -> completed.getNumber().getValue())
                .collect(Collectors.toSet());
        
        // Load current started challenge for user
        Optional<StartedChallenge> currentStartedChallenge = startedChallengeRepository.findById(userIdObj);
        Integer startedChallengeNumber = currentStartedChallenge
                .map(started -> started.getNumber().getValue())
                .orElse(null);
        
        // Populate status fields for each representation
        representations.forEach(repr -> {
            repr.setCompleted(completedNumbers.contains(repr.getNumber()));
            repr.setStarted(Objects.equals(repr.getNumber(), startedChallengeNumber));
            repr.setAvailable(false); // Hardcoded as per domain requirements
        });
    }
}
