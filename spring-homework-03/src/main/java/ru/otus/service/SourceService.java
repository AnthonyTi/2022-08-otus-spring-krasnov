package ru.otus.service;

import ru.otus.domain.Questionnaire;

import java.util.List;

public interface SourceService {

    List<Questionnaire> getQuestions();

}
