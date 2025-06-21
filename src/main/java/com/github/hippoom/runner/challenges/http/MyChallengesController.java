package com.github.hippoom.runner.challenges.http;

import com.github.hippoom.runner.challenges.domain.model.challenge.Challenge;
import com.github.hippoom.runner.challenges.domain.model.challenge.ChallengeRepository;
import com.github.hippoom.runner.challenges.domain.model.user.UserId;
import com.github.hippoom.runner.challenges.http.assembler.MyChallengeRepresentationAssembler;
import com.github.hippoom.runner.challenges.http.representation.MyChallengeRepresentation;
import com.github.hippoom.runner.challenges.http.support.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/my/challenges")
@RequiredArgsConstructor
public class MyChallengesController {

    private final ChallengeRepository challengeRepository;
    private final MyChallengeRepresentationAssembler assembler;

    @GetMapping
    public CollectionModel<MyChallengeRepresentation> listMyChallenges(@CurrentUser UserId userId) {
        // Get all challenges sorted by number
        List<Challenge> challenges = challengeRepository.findAll();

        return assembler.toCollectionModel(challenges);
    }
}
