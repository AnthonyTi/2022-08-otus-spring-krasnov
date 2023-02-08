package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.config.AppProps;
import ru.otus.service.ResourcesServiceImpl;
import ru.otus.service.SourceService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EnableConfigurationProperties(value = AppProps.class)
class SpringHomework03ApplicationTests {

    private SourceService sourceService;

    @Autowired
    private AppProps appProps;

    @BeforeEach
    void inti() {
        sourceService = new ResourcesServiceImpl(appProps);
    }


    @DisplayName("Сравнивает количество записей в qa.csv")
    @Test
    void getQuestions() {
        assertEquals(sourceService.getQuestions().size(), 5);
    }

}
