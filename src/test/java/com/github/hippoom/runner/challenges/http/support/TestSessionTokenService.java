package com.github.hippoom.runner.challenges.http.support;

import com.github.hippoom.runner.challenges.domain.user.UserId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Profile("test")
public class TestSessionTokenService implements SessionTokenService {
    
    private final Map<String, UserId> tokenMap = new ConcurrentHashMap<>();
    
    @Override
    public UserId getUserIdByToken(String token) {
        UserId userId = tokenMap.get(token);
        if (userId == null) {
            throw new IllegalArgumentException("Invalid session token: " + token);
        }
        return userId;
    }
    
    public void registerSession(String token, UserId userId) {
        tokenMap.put(token, userId);
    }
} 