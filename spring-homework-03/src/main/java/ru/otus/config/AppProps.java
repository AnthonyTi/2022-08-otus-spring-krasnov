package ru.otus.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Locale;

@Getter
@Setter
@ConfigurationProperties(prefix = "application")
public class AppProps {

    private String message;

    private Locale locale;

    private String greetings;

    private Integer passingScore;

    private Integer numberOfQuestions;

    private String resourcePath;

    private @Value("#{T(System).in}")InputStream is;

    private @Value("#{T(System).out}")PrintStream ps;

}
