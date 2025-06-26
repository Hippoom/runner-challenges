package com.github.hippoom.runner.challenges.domain.challenge.completion;

import com.github.hippoom.runner.challenges.domain.activity.UserActivity;
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