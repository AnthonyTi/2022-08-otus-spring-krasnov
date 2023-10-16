package ru.otus.service;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

import java.util.List;

public interface LibraryService {

    Genre addGenre(Genre genre);

    Author addAuthor(Author author);

    List<Genre> getAllGenres();

    List<Author> getAllAuthors();

    Book getBookById(String id);

    List<Book> getAllBooks();

    Book addBook(Book book);

    Book updateBook( Book book);

    void deleteBookById(String id);

    void addCommentByBookId(Comment comment, String id);

    List<Comment> getAllCommentsByBookId(String id);

}
