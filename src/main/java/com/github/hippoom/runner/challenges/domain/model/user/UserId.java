package com.github.hippoom.runner.challenges.domain.model.user;

import lombok.Value;
import java.util.UUID;

@Value
public class UserId {
    private String value;

    public UserId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("UserId cannot be null or empty");
        }
        this.value = value.trim();
    }

    public static UserId of(String value) {
        return new UserId(value);
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID().toString());
    }
}
