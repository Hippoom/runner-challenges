package com.github.hippoom.runner.challenges.domain.model.challenge;

import lombok.Value;

@Value
public class ChallengeNumber implements Comparable<ChallengeNumber> {
    private Integer value;

    public ChallengeNumber(Integer value) {
        if (value == null || value < 1) {
            throw new IllegalArgumentException("Challenge number must start from 1");
        }
        this.value = value;
    }

    public static ChallengeNumber of(Integer value) {
        return new ChallengeNumber(value);
    }

    @Override
    public int compareTo(ChallengeNumber other) {
        return this.value.compareTo(other.value);
    }
}
