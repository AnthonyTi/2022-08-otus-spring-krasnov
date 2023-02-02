package ru.otus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.dao.QuestionDao;
import ru.otus.dao.QuestionDaoImpl;
import ru.otus.service.QuestionnaireService;
import ru.otus.service.QuestionnaireServiceImpl;
import ru.otus.service.ResourcesServiceImpl;
import ru.otus.service.SourceService;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean("resourcesService")
    SourceService sourceService(@Value(value = "${resource-path}") final String path) {
        return new ResourcesServiceImpl(path);
    }

    @Bean
    QuestionDao questionDao(final SourceService sourceService) {
        return new QuestionDaoImpl(sourceService);
    }

    @Bean
    QuestionnaireService questionnaireService(@Value("${greetings:Hello there.}") String greetings,
                                              @Value("#{T(System).in}") InputStream is,
                                              @Value("#{T(System).out}") PrintStream ps,
                                              QuestionDao questionDao,
                                              @Value("${passing-score:4}") Integer passingScore,
                                              @Value("${number-of-questions:5}") Integer numberOfQuestions) {
        return new QuestionnaireServiceImpl(greetings, is, ps, questionDao, passingScore, numberOfQuestions);
    }
}
