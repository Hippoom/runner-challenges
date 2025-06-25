package com.github.hippoom.runner.challenges.domain.model.challenge;

import com.github.hippoom.runner.challenges.domain.model.user.UserId;
import org.springframework.stereotype.Component;

@Component
public class StartChallengeSpecification {
    
    public void validate(Challenge challenge, UserId userId) {
        if (challenge.isLocked()) {
            throw ChallengeUnavailableException.locked(challenge.getNumber());
        }
    }
} 