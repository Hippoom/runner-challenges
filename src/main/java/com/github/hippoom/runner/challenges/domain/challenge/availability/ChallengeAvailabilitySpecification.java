package com.github.hippoom.runner.challenges.domain.challenge.availability;

import com.github.hippoom.runner.challenges.domain.challenge.Challenge;
import com.github.hippoom.runner.challenges.domain.user.UserId;

public interface ChallengeAvailabilitySpecification {
    boolean test(Challenge challenge, UserId userId);
    void validate(Challenge challenge, UserId userId);
} 