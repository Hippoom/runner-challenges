package com.github.hippoom.runner.challenges.domain.activity;

import com.github.hippoom.runner.challenges.domain.user.UserId;
import lombok.Data;

import java.time.Instant;

@Data
public class UserActivity {
    private UserId userId;
    private Instant when;
    private String type;
    private UserActivityMetricSummary metricSummary;
}
