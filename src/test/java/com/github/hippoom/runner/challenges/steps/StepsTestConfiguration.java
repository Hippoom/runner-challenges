package com.github.hippoom.runner.challenges.steps;

import com.github.hippoom.runner.challenges.http.support.TestSessionTokenService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class StepsTestConfiguration {
    
    @Bean
    public TestSessionTokenService testSessionTokenService() {
        return new TestSessionTokenService();
    }
}
