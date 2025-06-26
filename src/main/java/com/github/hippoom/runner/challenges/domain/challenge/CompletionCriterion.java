package com.github.hippoom.runner.challenges.domain.challenge;

import com.github.hippoom.runner.challenges.domain.activity.UserActivity;

public interface CompletionCriterion {
    boolean isSatisfiedBy(UserActivity activity);
} 