package com.github.hippoom.runner.challenges.domain.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@Embeddable
public class UserId implements Serializable {
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
