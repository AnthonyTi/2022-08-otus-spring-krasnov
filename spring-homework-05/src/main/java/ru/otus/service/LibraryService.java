package ru.otus.service;

import ru.otus.domain.Book;

import java.util.List;

public interface LibraryService {

    Book getBookById(Long id);

    List<Book> getAllBooks();

    void addBook(Book book);

    void deleteBookById(Long id);

}
