package ru.otus.repository;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenreRepository {

    Genre insert(Genre genre);

    void deleteById(Long id);

    void update(Genre genre);

    Genre getById(Long id);

    List<Genre> getAll();

}
