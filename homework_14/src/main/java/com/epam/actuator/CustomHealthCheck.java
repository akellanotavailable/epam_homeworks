package com.epam.actuator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthCheck extends AbstractHealthIndicator {

    private final String projectName = "MySpringBootApp";

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        builder.up().withDetail("Name of project", projectName).build();
    }
}
