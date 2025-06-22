package com.github.hippoom.runner.challenges.messaging.spring;

import com.github.hippoom.runner.challenges.application.CompleteChallengeCommandHandler;
import com.github.hippoom.runner.challenges.domain.model.activity.UserActivity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserActivityEventListener {

    private final CompleteChallengeCommandHandler commandHandler;

    @EventListener
    public void handleUserActivity(UserActivity userActivity) {
        // Delegate to command handler to complete challenge
        commandHandler.handle(userActivity);
    }
} 