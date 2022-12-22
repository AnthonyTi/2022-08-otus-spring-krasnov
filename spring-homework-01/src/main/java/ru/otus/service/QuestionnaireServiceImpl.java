package ru.otus.service;

import ru.otus.dao.QuestionDao;
import ru.otus.domain.Questionnaire;

import java.util.List;
import java.util.Random;

public class QuestionnaireServiceImpl implements QuestionnaireService {
    private final QuestionDao dao;

    public QuestionnaireServiceImpl(final QuestionDao dao) {
        this.dao = dao;
    }

    @Override
    public void showRandomQuestionnaire() {
        List<Questionnaire> questionnaires = dao.getQuestions();
        if (!questionnaires.isEmpty()) {
            Questionnaire questionnaire = questionnaires.get(new Random().nextInt(questionnaires.size()));
            System.out.println("Question: " + questionnaire.getQuestion());
            System.out.println("Answer is: " + questionnaire.getAnswer());
        } else {
            System.out.println("THere are no any questionnaire!");
        }
    }
}
