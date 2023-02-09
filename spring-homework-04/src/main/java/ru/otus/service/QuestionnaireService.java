package ru.otus.service;

import ru.otus.domain.User;

public interface QuestionnaireService {

    void showRandomQuestionnaire();

    void startNewQuestionnaire(User user);

    void startNewQuestionnaire();

}
