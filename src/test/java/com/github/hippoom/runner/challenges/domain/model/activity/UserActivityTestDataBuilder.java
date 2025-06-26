package com.github.hippoom.runner.challenges.domain.model.activity;

import com.github.hippoom.runner.challenges.domain.model.user.UserId;

import java.time.Instant;

import static com.github.hippoom.runner.challenges.domain.model.user.UserIdTestDataBuilder.aUserId;

public class UserActivityTestDataBuilder {
    private UserActivity target = new UserActivity();

    public static UserActivityTestDataBuilder aUserActivity() {
        return new UserActivityTestDataBuilder()
            .withUserId(aUserId().build())
            .at(Instant.now());
    }

    public UserActivityTestDataBuilder withUserId(String userId) {
        target.setUserId(new UserId(userId));
        return this;
    }

    public UserActivityTestDataBuilder withUserId(UserId userId) {
        target.setUserId(userId);
        return this;
    }

    public UserActivityTestDataBuilder at(Instant when) {
        target.setWhen(when);
        return this;
    }
    
    public UserActivityTestDataBuilder withDistance(double distance) {
        ensureMetricSummary();
        target.getMetricSummary().setDistance(distance);
        return this;
    }
    
    public UserActivityTestDataBuilder withDuration(int durationSeconds) {
        ensureMetricSummary();
        target.getMetricSummary().setDuration(durationSeconds);
        return this;
    }
    
    public UserActivityTestDataBuilder withPace(double pacePerKm, double distance) {
        ensureMetricSummary();
        target.getMetricSummary().setDistance(distance);
        // Calculate duration from pace and distance
        int durationSeconds = (int) (pacePerKm * distance * 60);
        target.getMetricSummary().setDuration(durationSeconds);
        return this;
    }
    
    private void ensureMetricSummary() {
        if (target.getMetricSummary() == null) {
            target.setMetricSummary(new UserActivityMetricSummary());
        }
    }

    public UserActivity build() {
        return target;
    }
}
