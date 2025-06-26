package com.github.hippoom.runner.challenges.domain.challenge.availability;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import com.github.hippoom.runner.challenges.domain.challenge.Challenge;
import com.github.hippoom.runner.challenges.domain.user.UserId;

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