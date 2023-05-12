package ru.otus.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    private final String resourcePath;

    public AppConfig(@Value("${resource-path}") String resourcePath) {
        this.resourcePath = resourcePath;
    }
}
