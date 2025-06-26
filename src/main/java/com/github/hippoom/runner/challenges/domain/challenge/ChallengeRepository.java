package com.github.hippoom.runner.challenges.domain.challenge;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Repository;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@Repository
@ConfigurationProperties(prefix = "")
@Data
public class ChallengeRepository {
    private List<Challenge> challenges = new ArrayList<>();

    public List<Challenge> findAll() {
        return challenges.stream()
                .sorted(Comparator.comparing(Challenge::getNumber))
                .collect(Collectors.toList());
    }
    
    public Challenge getOrThrow(ChallengeNumber number) {
        return challenges.stream()
                .filter(challenge -> challenge.getNumber().equals(number))
                .findFirst()
                .orElseThrow(() -> new NoSuchChallengeException(number));
    }
}
