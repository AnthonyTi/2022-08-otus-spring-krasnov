package ru.otus.service;

import org.springframework.util.StringUtils;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Questionnaire;
import ru.otus.domain.User;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final InputStream is;

    private final PrintStream ps;

    private final String greetings;

    private final QuestionDao questionDao;

    private final Integer passingScore;

    private final Integer numberOfQuestions;

    public QuestionnaireServiceImpl(final String greetings,
                                    final InputStream is,
                                    final PrintStream ps,
                                    final QuestionDao questionDao,
                                    final Integer passingScore,
                                    final Integer numberOfQuestions) {
        this.is = is;
        this.ps = ps;
        this.greetings = greetings;
        this.questionDao = questionDao;
        this.passingScore = passingScore;
        this.numberOfQuestions = numberOfQuestions;
    }

    @Override
    public void showRandomQuestionnaire() {
        List<Questionnaire> questionnaires = questionDao.getQuestions();
        if (!questionnaires.isEmpty()) {
            Questionnaire questionnaire = questionnaires.get(new Random().nextInt(questionnaires.size()));
            ps.println("Question: " + questionnaire.getQuestion());
            ps.println("Answer is: " + questionnaire.getAnswer());
        } else {
            ps.println("There are no any questionnaire!");
        }
    }

    @Override
    public void startNewQuestionnaire() {
        List<Questionnaire> questionnaires = questionDao.getQuestions();
        if (questionnaires.size() < numberOfQuestions) {
            ps.println("Sorry, but this test is not ready yet.");
        } else {
            User user = getUser();
            ps.println("Answer to next questions and will see your scores.");
            for (int i = 0; i < questionnaires.size(); i++) {
                Questionnaire questionnaire = questionnaires.get(i);
                ps.println((i + 1) + ". " + questionnaire.getQuestion());
                Scanner scanner = new Scanner(is);
                String answer = scanner.nextLine();
                if (StringUtils.startsWithIgnoreCase(questionnaire.getAnswer(), answer)) {
                    user.incScores();
                }
            }
            printUserResult(user);
        }
    }

    private void printUserResult(final User user) {
        ps.println(user.getFirstName() + " " + user.getLastName() + " your final score is " + user.getScores());
        if (user.getScores() >= passingScore) {
            ps.println("Congratulations!!!! You pass this test!");
        } else {
            ps.println("Neah... Try next time.");
        }
    }

    private User getUser() {
        Scanner scanner = new Scanner(is);
        ps.println(greetings);
        ps.print("Enter Your first name: ");
        String firstName = scanner.nextLine();
        ps.print("Enter Your last name: ");
        String lastName = scanner.nextLine();
        return new User(firstName, lastName, 0);
    }
}
