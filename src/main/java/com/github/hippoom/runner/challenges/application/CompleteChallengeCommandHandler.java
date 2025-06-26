package com.github.hippoom.runner.challenges.application;

import com.github.hippoom.runner.challenges.domain.activity.UserActivity;
import com.github.hippoom.runner.challenges.domain.challenge.Challenge;
import com.github.hippoom.runner.challenges.domain.challenge.completion.ChallengeCompletionSpecification;
import com.github.hippoom.runner.challenges.domain.challenge.ChallengeRepository;
import com.github.hippoom.runner.challenges.domain.challenge.progress.CompletedChallenge;
import com.github.hippoom.runner.challenges.domain.challenge.progress.CompletedChallengeRepository;
import com.github.hippoom.runner.challenges.domain.challenge.progress.StartedChallenge;
import com.github.hippoom.runner.challenges.domain.challenge.progress.StartedChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompleteChallengeCommandHandler {

    private final StartedChallengeRepository startedChallengeRepository;
    private final ChallengeRepository challengeRepository;
    private final CompletedChallengeRepository completedChallengeRepository;
    private final ChallengeCompletionSpecification completionSpecification;

    @Transactional
    public void handle(UserActivity userActivity) {
        // 1. Load required entities early
        Optional<StartedChallenge> maybeStarted = startedChallengeRepository.findById(userActivity.getUserId());
        if (!maybeStarted.isPresent()) {
            return; // No started challenge to complete
        }

        StartedChallenge startedChallenge = maybeStarted.get();
        Challenge challenge = challengeRepository.getOrThrow(startedChallenge.getNumber());

        // 2. Validate preconditions early - using clean polymorphic approach
        if (!completionSpecification.canBeCompletedBy(challenge, userActivity)) {
            return; // Criteria not met
        }

        // 3. Execute business logic
        CompletedChallenge completedChallenge = new CompletedChallenge();
        completedChallenge.setUserId(userActivity.getUserId());
        completedChallenge.setNumber(challenge.getNumber());
        completedChallenge.setActivityId(UUID.randomUUID().toString());
        completedChallenge.setWhen(userActivity.getWhen());

        // 4. Persist changes
        completedChallengeRepository.save(completedChallenge);
    }
}
