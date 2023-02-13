package ru.otus.dao;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorDao {

    Author insert(Author author);

    void deleteById(Long id);

    void update(Author book);

    Author getById(Long id);

    List<Author> getAll();

}
