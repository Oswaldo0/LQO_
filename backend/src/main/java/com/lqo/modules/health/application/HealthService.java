package com.lqo.modules.health.application;

import com.lqo.modules.health.domain.HealthStatus;
import org.springframework.stereotype.Service;

@Service
public class HealthService {

    public HealthStatus getStatus() {
        return new HealthStatus("UP", "lqo-backend");
    }
}
