package ru.otus.domain;

public class Questionnaire {

    private final String question;
    private final String answer;

    public Questionnaire(final String question, final String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
