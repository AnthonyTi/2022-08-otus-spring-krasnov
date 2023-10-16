package ru.otus.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomActuator implements HealthIndicator {

    private final MongoTemplate mongoTemplate;

    @Override
    public Health health() {

        try {
            mongoTemplate.executeCommand("{ serverStatus: 1 }");
            return Health.up().build();
        }
        catch(Exception e) {
            log.error(e.getMessage());
            return Health.down().build();
        }
    }

}
