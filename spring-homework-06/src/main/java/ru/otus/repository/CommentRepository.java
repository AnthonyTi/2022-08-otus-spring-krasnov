package ru.otus.repository;

import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.List;

public interface CommentRepository {

    void deleteById(Long id);

    void update(Comment comment);

    Comment getById(Long id);

    List<Comment> getAllByBookId(Book book);

}
