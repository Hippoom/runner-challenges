package com.github.hippoom.runner.challenges.domain.model.challenge;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(of = "number")
@ToString(of = "number")
public class Challenge {
    private ChallengeNumber number;
    private boolean locked;
    private List<ChallengeNumber> prerequisites;
    private Double minimumDistance;
    private Double minimumPace;
}
