package com.github.hippoom.runner.challenges.application;

import com.github.hippoom.runner.challenges.command.StartChallengeCommand;
import com.github.hippoom.runner.challenges.domain.model.challenge.StartedChallenge;
import com.github.hippoom.runner.challenges.domain.model.challenge.StartedChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class StartChallengeCommandHandler {

    private final StartedChallengeRepository startedChallengeRepository;

    @Transactional
    public StartedChallenge handle(StartChallengeCommand command) {
        StartedChallenge startedChallenge = new StartedChallenge();
        startedChallenge.setUserId(command.getUserId());
        startedChallenge.setNumber(command.getChallengeNumber());
        startedChallenge.setWhen(Instant.now());

        return startedChallengeRepository.save(startedChallenge);
    }
}
