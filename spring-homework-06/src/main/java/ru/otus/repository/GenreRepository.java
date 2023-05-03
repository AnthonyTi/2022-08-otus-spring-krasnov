package ru.otus.repository;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenreRepository {

    Genre insert(Genre genre);

    void deleteGenre(Genre genre);

    void update(Genre genre);

    Genre getById(Long id);

    List<Genre> getAll();

}
