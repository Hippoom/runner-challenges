package com.github.hippoom.runner.challenges.domain.model.challenge;

import com.github.hippoom.runner.challenges.domain.model.user.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompletedChallengeRepository extends JpaRepository<CompletedChallenge, String> {
    
    List<CompletedChallenge> findByUserId(UserId userId);

}
