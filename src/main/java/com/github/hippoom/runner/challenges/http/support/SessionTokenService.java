package com.github.hippoom.runner.challenges.http.support;

import com.github.hippoom.runner.challenges.domain.user.UserId;

public interface SessionTokenService {
    UserId getUserIdByToken(String token);
} 