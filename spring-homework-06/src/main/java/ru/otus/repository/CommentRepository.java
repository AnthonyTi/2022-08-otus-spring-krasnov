package ru.otus.repository;

import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.List;

public interface CommentRepository {

    Long count();

    Comment addComment(Comment comment);

    void deleteComment(Comment comment);

    Comment update(Comment comment);

    Comment getById(Long id);

    List<Comment> getAllByBookId(Book book);

}
