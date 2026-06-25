package com.lqo.modules.health.web;

import com.lqo.modules.health.application.HealthService;
import com.lqo.modules.health.domain.HealthStatus;
import com.lqo.shared.web.ApiRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiRoutes.API_V1 + "/health")
public class HealthController {

    private final HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    @GetMapping
    public HealthStatus status() {
        return healthService.getStatus();
    }
}
