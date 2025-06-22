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

    public UserActivity build() {
        return target;
    }
}
