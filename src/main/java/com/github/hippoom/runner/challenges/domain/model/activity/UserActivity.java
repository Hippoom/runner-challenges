package com.github.hippoom.runner.challenges.domain.model.activity;

import com.github.hippoom.runner.challenges.domain.model.user.UserId;
import lombok.Data;

import java.time.Instant;

@Data
public class UserActivity {
    private UserId userId;
    private Instant when;
} 