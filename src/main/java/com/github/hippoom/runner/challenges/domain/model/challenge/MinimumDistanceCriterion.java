package com.github.hippoom.runner.challenges.domain.model.challenge;

import com.github.hippoom.runner.challenges.domain.model.activity.UserActivity;
import lombok.Value;

@Value
public class MinimumDistanceCriterion implements CompletionCriterion {
    private final Double minimumDistance;
    
    @Override
    public boolean isSatisfiedBy(UserActivity activity) {
        if (activity.getMetricSummary() == null) {
            return false;
        }
        return activity.getMetricSummary().getDistance() >= minimumDistance;
    }
} 