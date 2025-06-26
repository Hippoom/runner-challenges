package com.github.hippoom.runner.challenges.http;

import com.github.hippoom.runner.challenges.application.StartChallengeCommandHandler;
import com.github.hippoom.runner.challenges.command.StartChallengeCommand;
import com.github.hippoom.runner.challenges.domain.challenge.Challenge;
import com.github.hippoom.runner.challenges.domain.challenge.ChallengeNumber;
import com.github.hippoom.runner.challenges.domain.challenge.ChallengeRepository;
import com.github.hippoom.runner.challenges.domain.challenge.progress.StartedChallenge;
import com.github.hippoom.runner.challenges.domain.user.UserId;
import com.github.hippoom.runner.challenges.http.assembler.MyChallengeRepresentationAssembler;
import com.github.hippoom.runner.challenges.http.representation.MyChallengeRepresentation;
import com.github.hippoom.runner.challenges.http.support.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/my/challenges")
@RequiredArgsConstructor
public class MyChallengesController {

    private final ChallengeRepository challengeRepository;
    private final MyChallengeRepresentationAssembler assembler;
    private final StartChallengeCommandHandler commandHandler;

    @GetMapping
    public CollectionModel<MyChallengeRepresentation> listMyChallenges(@CurrentUser UserId userId) {
        // Get all challenges sorted by number
        List<Challenge> challenges = challengeRepository.findAll();

        // Use optimized toModels method to avoid N+1 queries
        List<MyChallengeRepresentation> challengeRepresentations =
                assembler.toModels(challenges, userId);

        return CollectionModel.of(challengeRepresentations);
    }

    @PostMapping("/{number}/start")
    public MyChallengeRepresentation startChallenge(@PathVariable("number") int number, @CurrentUser UserId userId) {
        ChallengeNumber challengeNumber = new ChallengeNumber(number);
        StartChallengeCommand command = new StartChallengeCommand(challengeNumber, userId);
        StartedChallenge startedChallenge = commandHandler.handle(command);

        return assembler.toModel(startedChallenge);
    }
}
