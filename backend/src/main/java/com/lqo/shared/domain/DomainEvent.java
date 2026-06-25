package com.lqo.shared.domain;

import java.time.Instant;

public interface DomainEvent {

    Instant occurredAt();
}
