package ru.otus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Questionnaire;

import java.util.List;
import java.util.Random;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final QuestionDao dao;
    private final Integer amountOfQuestions;

    public QuestionnaireServiceImpl(final QuestionDao dao, @Value("${amount-of-questions:5}") Integer amountOfQuestions) {
        this.dao = dao;
        this.amountOfQuestions = amountOfQuestions;
    }

    @Override
    public void showRandomQuestionnaire() {
        List<Questionnaire> questionnaires = dao.getQuestions();
        if (!questionnaires.isEmpty()) {
            Questionnaire questionnaire = questionnaires.get(new Random().nextInt(questionnaires.size()));
            System.out.println("Question: " + questionnaire.getQuestion());
            System.out.println("Answer is: " + questionnaire.getAnswer());
        } else {
            System.out.println("There are no any questionnaire!");
        }
    }

    @Override
    public List<Questionnaire> getQuestions() {
        return null;
    }


}
