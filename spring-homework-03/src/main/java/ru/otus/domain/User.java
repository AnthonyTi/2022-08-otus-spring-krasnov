package ru.otus.domain;

public class User {

    private final String firstName;

    private final String lastName;

    private Integer scores;

    public User(final String firstName, final String lastName, final Integer scores) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.scores = scores;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getScores() {
        return scores;
    }

    public void incScores() {
        scores++;
    }
}
