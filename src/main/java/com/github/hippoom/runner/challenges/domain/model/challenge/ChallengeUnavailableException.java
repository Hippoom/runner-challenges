package com.github.hippoom.runner.challenges.domain.model.challenge;

public final class ChallengeUnavailableException extends RuntimeException {
    
    public static ChallengeUnavailableException locked(ChallengeNumber challengeNumber) {
        return new ChallengeUnavailableException(challengeNumber, "it is locked");
    }
    
    public static ChallengeUnavailableException prerequisitesNotMet(ChallengeNumber challengeNumber) {
        return new ChallengeUnavailableException(challengeNumber, "prerequisites are not met");
    }
    
    private ChallengeUnavailableException(ChallengeNumber challengeNumber, String reason) {
        super("Cannot start challenge " + challengeNumber.getValue() + " because " + reason);
    }
} 