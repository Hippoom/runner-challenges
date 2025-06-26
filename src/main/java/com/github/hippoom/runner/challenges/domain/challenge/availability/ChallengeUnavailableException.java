package com.github.hippoom.runner.challenges.domain.challenge.availability;

import com.github.hippoom.runner.challenges.domain.challenge.ChallengeNumber;

public final class ChallengeUnavailableException extends RuntimeException {
    
    public static ChallengeUnavailableException locked(ChallengeNumber challengeNumber) {
        return new ChallengeUnavailableException(challengeNumber, "locked");
    }
    
    public static ChallengeUnavailableException prerequisitesNotMet(ChallengeNumber challengeNumber) {
        return new ChallengeUnavailableException(challengeNumber, "prerequisites not met");
    }
    
    private ChallengeUnavailableException(ChallengeNumber challengeNumber, String reason) {
        super("Challenge " + challengeNumber.getValue() + " is " + reason);
    }
} 