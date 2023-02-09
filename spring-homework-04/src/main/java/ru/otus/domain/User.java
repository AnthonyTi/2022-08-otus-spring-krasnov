package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class User {

    private final String firstName;

    private final String lastName;

    private Integer scores;

    public void incScores() {
        scores++;
    }
}
