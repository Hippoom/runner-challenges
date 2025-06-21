package com.github.hippoom.runner.challenges.http.assembler;

import com.github.hippoom.runner.challenges.domain.model.challenge.Challenge;
import com.github.hippoom.runner.challenges.http.representation.MyChallengeRepresentation;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class MyChallengeRepresentationAssembler 
        implements RepresentationModelAssembler<Challenge, MyChallengeRepresentation> {

    @Override
    public MyChallengeRepresentation toModel(Challenge challenge) {
        MyChallengeRepresentation repr = new MyChallengeRepresentation();
        repr.setNumber(challenge.getNumber().getValue());
        repr.setCompleted(challenge.isCompleted());
        repr.setAvailable(challenge.isAvailable());
        repr.setStarted(challenge.isStarted());

        // TODO: Add HATEOAS links when start endpoint is implemented
        return repr;
    }
}
