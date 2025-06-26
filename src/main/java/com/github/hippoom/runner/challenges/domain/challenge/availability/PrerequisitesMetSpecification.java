package com.github.hippoom.runner.challenges.domain.challenge.availability;

import com.github.hippoom.runner.challenges.domain.challenge.Challenge;
import com.github.hippoom.runner.challenges.domain.challenge.ChallengeNumber;
import com.github.hippoom.runner.challenges.domain.challenge.progress.CompletedChallenge;
import com.github.hippoom.runner.challenges.domain.challenge.progress.CompletedChallengeRepository;
import com.github.hippoom.runner.challenges.domain.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Qualifier("prerequisitesMetSpec")
@RequiredArgsConstructor
public class PrerequisitesMetSpecification implements ChallengeAvailabilitySpecification {
    
    private final CompletedChallengeRepository completedChallengeRepository;
    
    @Override
    public boolean test(Challenge challenge, UserId userId) {
        if (challenge.getPrerequisites() == null || challenge.getPrerequisites().isEmpty()) {
            return true; // No prerequisites
        }
        
        Set<Integer> completedNumbers = getCompletedChallengeNumbers(userId);
        return challenge.getPrerequisites().stream()
                .allMatch(prereq -> completedNumbers.contains(prereq.getValue()));
    }
    
    @Override
    public void validate(Challenge challenge, UserId userId) {
        if (!test(challenge, userId)) {
            throw ChallengeUnavailableException.prerequisitesNotMet(challenge.getNumber());
        }
    }
    
    private Set<Integer> getCompletedChallengeNumbers(UserId userId) {
        return completedChallengeRepository.findByUserId(userId).stream()
                .map(CompletedChallenge::getNumber)
                .map(ChallengeNumber::getValue)
                .collect(Collectors.toSet());
    }
} 