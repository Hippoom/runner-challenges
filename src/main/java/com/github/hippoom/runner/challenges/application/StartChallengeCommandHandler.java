package com.github.hippoom.runner.challenges.application;

import com.github.hippoom.runner.challenges.command.StartChallengeCommand;
import com.github.hippoom.runner.challenges.domain.challenge.Challenge;
import com.github.hippoom.runner.challenges.domain.challenge.ChallengeRepository;
import com.github.hippoom.runner.challenges.domain.challenge.availability.StartChallengeSpecification;
import com.github.hippoom.runner.challenges.domain.challenge.progress.StartedChallenge;
import com.github.hippoom.runner.challenges.domain.challenge.progress.StartedChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class StartChallengeCommandHandler {

    private final ChallengeRepository challengeRepository;
    private final StartedChallengeRepository startedChallengeRepository;
    private final StartChallengeSpecification specification;

    @Transactional
    public StartedChallenge handle(StartChallengeCommand command) {
        // 1. Load required entities early
        Challenge challenge = challengeRepository.getOrThrow(command.getChallengeNumber());
        
        // 2. Validate preconditions early
        specification.validate(challenge, command.getUserId());
        
        // 3. Execute business logic
        StartedChallenge startedChallenge = new StartedChallenge();
        startedChallenge.setUserId(command.getUserId());
        startedChallenge.setNumber(command.getChallengeNumber());
        startedChallenge.setWhen(Instant.now());

        // 4. Persist changes
        return startedChallengeRepository.save(startedChallenge);
    }
}
