package com.github.hippoom.runner.challenges.domain.challenge;

public final class NoSuchChallengeException extends RuntimeException {
    
    public NoSuchChallengeException(ChallengeNumber challengeNumber) {
        super("No such challenge: " + challengeNumber.getValue());
    }
} 