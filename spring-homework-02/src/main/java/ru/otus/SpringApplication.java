package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.configuration.AppConfig;

public class SpringApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);


    }
}