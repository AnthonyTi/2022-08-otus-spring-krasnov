package ru.otus;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.config.AppConfig;
import ru.otus.service.QuestionnaireService;

@ComponentScan

public class SpringApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        QuestionnaireService questionnaireService = context.getBean(QuestionnaireService.class);
        questionnaireService.startNewQuestionnaire();
    }
}