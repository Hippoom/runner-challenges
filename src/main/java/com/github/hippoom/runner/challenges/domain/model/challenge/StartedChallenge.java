package com.github.hippoom.runner.challenges.domain.model.challenge;

import com.github.hippoom.runner.challenges.domain.model.user.UserId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;

@Data
@EqualsAndHashCode(of = "userId")
@ToString(of = "userId")
public class StartedChallenge {
    private UserId userId;
    private ChallengeNumber number;
    private Instant when;
}
