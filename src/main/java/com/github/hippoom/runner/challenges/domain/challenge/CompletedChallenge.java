package com.github.hippoom.runner.challenges.domain.challenge;

import com.github.hippoom.runner.challenges.domain.user.UserId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.AttributeOverride;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "challenge_completed")
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class CompletedChallenge {
    @Id
    @Column(name = "id")
    private String id = UUID.randomUUID().toString();
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    private UserId userId;
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "challenge_number"))
    private ChallengeNumber number;
    
    @Column(name = "activity_id")
    private String activityId;
    
    @Column(name = "when_")
    private Instant when;
} 