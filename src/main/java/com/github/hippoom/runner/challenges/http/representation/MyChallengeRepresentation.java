package com.github.hippoom.runner.challenges.http.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "challenges")
public class MyChallengeRepresentation extends RepresentationModel<MyChallengeRepresentation> {
    private Integer number;

    @JsonProperty("is_completed")
    private boolean isCompleted;

    @JsonProperty("is_available")
    private boolean isAvailable;

    @JsonProperty("is_started")
    private boolean isStarted;

    @JsonProperty("minimum_distance")
    private Double minimumDistance;

    @JsonProperty("minimum_pace")
    private Double minimumPace;
}
