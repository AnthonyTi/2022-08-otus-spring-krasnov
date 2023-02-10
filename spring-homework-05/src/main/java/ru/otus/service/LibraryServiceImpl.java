package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.BookDao;
import ru.otus.domain.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookDao bookDao;


    @Override
    public Book getBookById(final Long id) {
        Book book = bookDao.getById(id);
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> bookList = bookDao.getAll();
        return bookList;
    }

    @Override
    public void addBook(Book book) {
        bookDao.insert(book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookDao.deleteById(id);
    }
}
