package com.github.hippoom.runner.challenges.domain.challenge;

import com.github.hippoom.runner.challenges.domain.user.UserId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EmbeddedId;
import javax.persistence.Embedded;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import java.time.Instant;

@Entity
@Table(name = "started_challenge")
@Data
@EqualsAndHashCode(of = "userId")
@ToString(of = "userId")
public class StartedChallenge {
    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    private UserId userId;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "challenge_number"))
    private ChallengeNumber number;
    
    @Column(name = "when_")
    private Instant when;
}
