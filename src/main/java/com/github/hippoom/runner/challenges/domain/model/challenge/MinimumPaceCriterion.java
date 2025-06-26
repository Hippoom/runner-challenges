package com.github.hippoom.runner.challenges.domain.model.challenge;

import com.github.hippoom.runner.challenges.domain.model.activity.UserActivity;
import lombok.Value;

@Value
public class MinimumPaceCriterion implements CompletionCriterion {
    private static final double SECONDS_TO_MINUTES = 60.0;
    
    private final Double minimumPace; // minutes per km
    
    @Override
    public boolean isSatisfiedBy(UserActivity activity) {
        if (activity.getMetricSummary() == null) {
            return false;
        }
        
        double distance = activity.getMetricSummary().getDistance();
        int durationSeconds = activity.getMetricSummary().getDuration();
        
        if (distance <= 0) {
            return false;
        }
        
        // Calculate pace: minutes per km
        double pacePerKm = (durationSeconds / SECONDS_TO_MINUTES) / distance;
        
        // Lower pace is better (faster running)
        return pacePerKm <= minimumPace;
    }
} 