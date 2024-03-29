package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.config.AppProps;
import ru.otus.service.QuestionnaireService;
import ru.otus.service.QuestionnaireServiceImpl;

@SpringBootApplication
@EnableConfigurationProperties(AppProps.class)
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        QuestionnaireService service = context.getBean(QuestionnaireServiceImpl.class);
        service.startNewQuestionnaire();


    }

}
