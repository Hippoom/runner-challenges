package com.github.hippoom.runner.challenges.domain.model.challenge;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class ChallengeNumberConverter implements Converter<String, ChallengeNumber> {

    @Override
    public ChallengeNumber convert(String source) {
        try {
            return source != null && !source.trim().isEmpty()
                ? ChallengeNumber.of(Integer.parseInt(source.trim()))
                : null;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot convert '" + source + "' to ChallengeNumber", e);
        }
    }
}
