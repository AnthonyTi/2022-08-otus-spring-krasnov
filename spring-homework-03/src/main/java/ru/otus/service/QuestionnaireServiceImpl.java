package ru.otus.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.config.AppProps;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Questionnaire;
import ru.otus.domain.User;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final MessageSource messageSource;
    private final AppProps appProps;

    private final QuestionDao questionDao;

    public QuestionnaireServiceImpl(final MessageSource messageSource, final AppProps appProps, final QuestionDao questionDao) {
        this.messageSource = messageSource;
        this.appProps = appProps;
        this.questionDao = questionDao;
    }

    @Override
    public void showRandomQuestionnaire() {
        List<Questionnaire> questionnaires = questionDao.getQuestions();
        if (!questionnaires.isEmpty()) {
            Questionnaire questionnaire = questionnaires.get(new Random().nextInt(questionnaires.size()));
            appProps.getPs().println(messageSource.getMessage("questionnaire.question",
                    new String[]{questionnaire.getQuestion()},
                    appProps.getLocale()));
            appProps.getPs().println(messageSource.getMessage("questionnaire.answer",
                    new String[]{questionnaire.getAnswer()},
                    appProps.getLocale()));
        } else {
            appProps.getPs().println(messageSource.getMessage("error.no-questionnaire",
                    null,
                    appProps.getLocale()));
        }
    }

    @Override
    public void startNewQuestionnaire() {
        List<Questionnaire> questionnaires = questionDao.getQuestions();
        if (questionnaires.size() < appProps.getNumberOfQuestions()) {
            appProps.getPs().println(messageSource.getMessage("error.not-ready",
                    null,
                    appProps.getLocale()));
        } else {
            User user = getUser();
            appProps.getPs().println(messageSource.getMessage("guide",
                    null,
                    appProps.getLocale()));
            for (int i = 0; i < questionnaires.size(); i++) {
                Questionnaire questionnaire = questionnaires.get(i);
                appProps.getPs().println((i + 1) + ". " + questionnaire.getQuestion());
                Scanner scanner = new Scanner(appProps.getIs());
                String answer = scanner.nextLine();
                if (StringUtils.startsWithIgnoreCase(questionnaire.getAnswer(), answer)) {
                    user.incScores();
                }
            }
            printUserResult(user);
        }
    }

    private void printUserResult(final User user) {
        appProps.getPs().println(messageSource.getMessage("questionnaire.result",
                new String[]{user.getFirstName(), user.getLastName(), Integer.toString(user.getScores())},
                appProps.getLocale()));
        if (user.getScores() >= appProps.getPassingScore()) {
            appProps.getPs().println(messageSource.getMessage("questionnaire.congrats",
                    null,
                    appProps.getLocale()));
        } else {
            appProps.getPs().println(messageSource.getMessage("questionnaire.failure",
                    null,
                    appProps.getLocale()));
        }
    }

    private User getUser() {
        Scanner scanner = new Scanner(appProps.getIs());
        appProps.getPs().println(messageSource.getMessage("greetings", null, appProps.getLocale()));
        appProps.getPs().print(messageSource.getMessage("user.name", null, appProps.getLocale()));
        String firstName = scanner.nextLine();
        appProps.getPs().print(messageSource.getMessage("user.lastname", null, appProps.getLocale()));
        String lastName = scanner.nextLine();
        return new User(firstName, lastName, 0);
    }
}
