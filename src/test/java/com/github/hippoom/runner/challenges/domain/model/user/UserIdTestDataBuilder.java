package com.github.hippoom.runner.challenges.domain.model.user;

public final class UserIdTestDataBuilder {
    private UserId target;

    private UserIdTestDataBuilder(UserId userId) {
        this.target = userId;
    }

    public static UserIdTestDataBuilder aUserId() {
        return new UserIdTestDataBuilder(UserId.generate());
    }

    public UserIdTestDataBuilder withValue(String value) {
        this.target = new UserId(value);
        return this;
    }

    public UserId build() {
        return target;
    }
} 