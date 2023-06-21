package ru.otus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@EnableConfigurationProperties
@ComponentScan({"ru.otus.repository"})
class SpringHomework08ApplicationTests {

    @Test
    void contextLoads() {
    }

}
