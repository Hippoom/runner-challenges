package com.github.hippoom.runner.challenges.domain.challenge.completion;

import com.github.hippoom.runner.challenges.domain.activity.UserActivity;
import com.github.hippoom.runner.challenges.domain.challenge.Challenge;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChallengeCompletionSpecification {
    
    public boolean canBeCompletedBy(Challenge challenge, UserActivity activity) {
        if (activity == null) {
            return false;
        }
        
        List<CompletionCriterion> criteria = createCompletionCriteria(challenge);
        
        // No criteria means any activity completes the challenge
        if (criteria.isEmpty()) {
            return true;
        }
        
        // Elegant polymorphic validation using allMatch
        return criteria.stream()
                .allMatch(criterion -> criterion.isSatisfiedBy(activity));
    }
    
    private List<CompletionCriterion> createCompletionCriteria(Challenge challenge) {
        List<CompletionCriterion> criteria = new ArrayList<>();
        
        if (challenge.getMinimumDistance() != null) {
            criteria.add(new MinimumDistanceCriterion(challenge.getMinimumDistance()));
        }
        
        if (challenge.getMinimumPace() != null) {
            criteria.add(new MinimumPaceCriterion(challenge.getMinimumPace()));
        }
        
        return criteria;
    }
} 