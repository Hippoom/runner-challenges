package com.github.hippoom.runner.challenges.config;

import com.github.hippoom.runner.challenges.domain.challenge.availability.ChallengeAvailabilitySpecification;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SpecificationConfiguration {
    
    @Bean
    public List<ChallengeAvailabilitySpecification> challengeAvailabilitySpecs(
            @Qualifier("challengeNotLockedSpec") ChallengeAvailabilitySpecification notLockedSpec,
            @Qualifier("prerequisitesMetSpec") ChallengeAvailabilitySpecification prerequisitesSpec
    ) {
        return Arrays.asList(notLockedSpec, prerequisitesSpec);
    }
} 