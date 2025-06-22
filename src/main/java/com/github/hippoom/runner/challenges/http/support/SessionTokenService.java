package com.github.hippoom.runner.challenges.http.support;

import com.github.hippoom.runner.challenges.domain.model.user.UserId;

public interface SessionTokenService {
    UserId getUserIdByToken(String token);
} 