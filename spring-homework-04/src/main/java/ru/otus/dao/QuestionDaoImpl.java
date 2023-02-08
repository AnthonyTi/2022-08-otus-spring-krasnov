package ru.otus.dao;

import org.springframework.stereotype.Component;
import ru.otus.domain.Questionnaire;
import ru.otus.service.SourceService;

import java.util.List;

@Component
public class QuestionDaoImpl implements QuestionDao {

    private final SourceService sourceService;

    public QuestionDaoImpl(final SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @Override
    public List<Questionnaire> getQuestions() {
        return sourceService.getQuestions();
    }

}
