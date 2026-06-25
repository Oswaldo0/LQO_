package com.lqo.modules.health.domain;

import java.time.Instant;

public record HealthStatus(String status, String service, Instant checkedAt) {

    public HealthStatus(String status, String service) {
        this(status, service, Instant.now());
    }
}
