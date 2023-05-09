package ru.otus.service;

import ru.otus.domain.Questionnaire;
import java.util.List;

public interface QuestionnaireService {

    void showRandomQuestionnaire();

    List<Questionnaire> getQuestions();

}
