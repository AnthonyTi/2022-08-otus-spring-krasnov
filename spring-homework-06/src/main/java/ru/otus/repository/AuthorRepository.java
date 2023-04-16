package ru.otus.repository;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorRepository {

    Author insert(Author author);

    void deleteById(Long id);

    void update(Author author);

    Author getById(Long id);

    List<Author> getAll();

}
