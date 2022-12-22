package ru.otus.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.service.SourceService;
import ru.otus.service.ResourcesServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тест IOService")
public class ResourcesServiceImplTest {

    private SourceService sourceService;

    @BeforeEach
    void setUp() {
        sourceService = new ResourcesServiceImpl("qa.csv");
    }

    @DisplayName("Сравнивает количество записей в qa.csv")
    @Test
    void getQuestions() {
        assertEquals(sourceService.getQuestions().size(), 5);
    }
}