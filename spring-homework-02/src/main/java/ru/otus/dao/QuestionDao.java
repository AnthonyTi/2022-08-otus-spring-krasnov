package ru.otus.dao;

import ru.otus.domain.Questionnaire;

import java.util.List;

public interface QuestionDao {

    List<Questionnaire> getQuestions();

}
