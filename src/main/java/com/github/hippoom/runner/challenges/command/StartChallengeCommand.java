package com.github.hippoom.runner.challenges.command;

import com.github.hippoom.runner.challenges.domain.model.challenge.ChallengeNumber;
import com.github.hippoom.runner.challenges.domain.model.user.UserId;
import lombok.Data;

@Data
public class StartChallengeCommand {
    private final ChallengeNumber challengeNumber;
    private final UserId userId;
} 