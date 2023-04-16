package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.repository.BookRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Book getBookById(final Long id) {
        Book book = bookRepository.getById(id);
        return book;
    }

    @Override
    @Transactional
    public List<Book> getAllBooks() {
        List<Book> bookList = bookRepository.getAll();
        return bookList;
    }

    @Override
    @Transactional
    public Book addBook(Book book) {
        return bookRepository.insert(book);
    }

    @Override
    @Transactional
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addCommentByBookId(Comment comment, Long id) {
        bookRepository.addCommentToBook(comment, id);
    }

    @Override
    @Transactional
    public List<Comment> getAllCommentsByBookId(Long id) {
        return bookRepository.getById(id).getComments();
    }
}
