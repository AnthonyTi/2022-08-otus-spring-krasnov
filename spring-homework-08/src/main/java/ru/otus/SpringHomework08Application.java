package ru.otus;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableMongock
@EnableConfigurationProperties
@SpringBootApplication
public class SpringHomework08Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringHomework08Application.class, args);
    }

}
