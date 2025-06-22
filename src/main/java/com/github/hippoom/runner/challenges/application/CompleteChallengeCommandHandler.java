package com.github.hippoom.runner.challenges.application;

import com.github.hippoom.runner.challenges.domain.model.activity.UserActivity;
import com.github.hippoom.runner.challenges.domain.model.challenge.ChallengeNumber;
import com.github.hippoom.runner.challenges.domain.model.challenge.CompletedChallenge;
import com.github.hippoom.runner.challenges.domain.model.challenge.CompletedChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompleteChallengeCommandHandler {

    private final CompletedChallengeRepository completedChallengeRepository;

    @Transactional
    public CompletedChallenge handle(UserActivity userActivity) {
        CompletedChallenge completedChallenge = new CompletedChallenge();
        completedChallenge.setUserId(userActivity.getUserId());
        completedChallenge.setNumber(new ChallengeNumber(1));
        completedChallenge.setActivityId(UUID.randomUUID().toString()); // Generate activity ID
        completedChallenge.setWhen(userActivity.getWhen());

        return completedChallengeRepository.save(completedChallenge);
    }
}
