package ru.otus.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
@Getter
@Setter
public class IOConfig {

    private @Value("#{T(System).in}") InputStream is;

    private @Value("#{T(System).out}") PrintStream ps;

}
