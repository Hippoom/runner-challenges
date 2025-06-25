package com.github.hippoom.runner.challenges.domain.model.challenge;

import com.github.hippoom.runner.challenges.domain.model.user.UserId;

public interface ChallengeAvailabilitySpecification {
    boolean test(Challenge challenge, UserId userId);
    void validate(Challenge challenge, UserId userId);
} 