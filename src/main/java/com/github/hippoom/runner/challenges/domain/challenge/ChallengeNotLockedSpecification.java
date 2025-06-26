package com.github.hippoom.runner.challenges.domain.challenge;

import com.github.hippoom.runner.challenges.domain.user.UserId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("challengeNotLockedSpec")
public class ChallengeNotLockedSpecification implements ChallengeAvailabilitySpecification {
    
    @Override
    public boolean test(Challenge challenge, UserId userId) {
        return !challenge.isLocked();
    }
    
    @Override
    public void validate(Challenge challenge, UserId userId) {
        if (challenge.isLocked()) {
            throw ChallengeUnavailableException.locked(challenge.getNumber());
        }
    }
} 