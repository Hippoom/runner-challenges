package com.github.hippoom.runner.challenges.domain.model.challenge;

public final class NoSuchChallengeException extends RuntimeException {
    
    public NoSuchChallengeException(ChallengeNumber challengeNumber) {
        super("No such challenge: " + challengeNumber.getValue());
    }
} 