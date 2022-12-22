package ru.otus;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.service.QuestionnaireServiceImpl;

public class SpringApplication {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        QuestionnaireServiceImpl questionnaireService = context.getBean("questionnaireService", QuestionnaireServiceImpl.class);
        questionnaireService.showRandomQuestionnaire();
    }
}