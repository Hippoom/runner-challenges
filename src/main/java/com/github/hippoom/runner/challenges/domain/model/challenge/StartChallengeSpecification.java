package com.github.hippoom.runner.challenges.domain.model.challenge;

import com.github.hippoom.runner.challenges.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Primary
@Component
@RequiredArgsConstructor
public class StartChallengeSpecification implements ChallengeAvailabilitySpecification {
    
    private final List<ChallengeAvailabilitySpecification> specifications;
    
    @Override
    public boolean test(Challenge challenge, UserId userId) {
        return specifications.stream()
                .allMatch(spec -> spec.test(challenge, userId));
    }
    
    @Override
    public void validate(Challenge challenge, UserId userId) {
        specifications.forEach(spec -> spec.validate(challenge, userId));
    }
} 