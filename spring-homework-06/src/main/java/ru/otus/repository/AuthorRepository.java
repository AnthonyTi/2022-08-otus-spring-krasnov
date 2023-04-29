package ru.otus.repository;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorRepository {

    Author insert(Author author);

    void deleteAuthor(Author author);

    Author update(Author author);

    Author getById(Long id);

    List<Author> getAll();

}
