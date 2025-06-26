package com.github.hippoom.runner.challenges.domain.activity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserActivityMetricSummary {
    private double distance;
    private int duration;
}
