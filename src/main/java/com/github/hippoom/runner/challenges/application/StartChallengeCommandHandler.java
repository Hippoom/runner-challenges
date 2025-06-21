package com.github.hippoom.runner.challenges.application;

import com.github.hippoom.runner.challenges.command.StartChallengeCommand;
import com.github.hippoom.runner.challenges.domain.model.challenge.StartedChallenge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class StartChallengeCommandHandler {

    public StartedChallenge handle(StartChallengeCommand command) {
        // For now, simply create a StartedChallenge from the command
        StartedChallenge startedChallenge = new StartedChallenge();
        startedChallenge.setUserId(command.getUserId());
        startedChallenge.setNumber(command.getChallengeNumber());
        startedChallenge.setWhen(Instant.now());
        return startedChallenge;
    }
}
