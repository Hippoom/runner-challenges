package com.github.hippoom.runner.challenges.domain.challenge.progress;

import com.github.hippoom.runner.challenges.domain.user.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StartedChallengeRepository extends JpaRepository<StartedChallenge, UserId> {


}
