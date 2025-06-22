package com.github.hippoom.runner.challenges.domain.model.challenge;

import com.github.hippoom.runner.challenges.domain.model.user.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StartedChallengeRepository extends JpaRepository<StartedChallenge, UserId> {


}
