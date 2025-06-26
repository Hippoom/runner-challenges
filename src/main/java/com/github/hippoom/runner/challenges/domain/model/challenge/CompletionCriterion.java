package com.github.hippoom.runner.challenges.domain.model.challenge;

import com.github.hippoom.runner.challenges.domain.model.activity.UserActivity;

public interface CompletionCriterion {
    boolean isSatisfiedBy(UserActivity activity);
} 