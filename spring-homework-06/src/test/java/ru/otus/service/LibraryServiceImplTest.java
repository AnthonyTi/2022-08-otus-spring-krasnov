package ru.otus.service;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.repository.BookRepositoryJpa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import({LibraryServiceImpl.class, BookRepositoryJpa.class})
class LibraryServiceImplTest {

    @Autowired
    private LibraryServiceImpl libraryService;
    private final Author author = new Author("Ivan");
    private final Genre genre = new Genre("Science");

    @BeforeEach
    void init() {
        author.setId(1L);
        genre.setId(1L);
    }

    @DisplayName("Должен вернуть книгу и проверить что у нее есть 2 комментария")
    @Test
    @Transactional
    void getBookById() {
        Book actualBook = libraryService.getBookById(1L);
        assertThat(actualBook.getComments()).hasSize(2);
    }

    @DisplayName("Должен проверить список книг")
    @Test
    @Transactional
    void getAllBook() {
        List<Book> actualBookList = libraryService.getAllBooks();
        assertThat(actualBookList).hasSize(2);
        assertThat(actualBookList.get(0).getComments()).hasSize(2);
    }

    @DisplayName("Должен проверить добавление книги в библиотеку")
    @Test
    @Transactional
    void addBook() {
        val addedBook = libraryService.addBook(new Book("Book for testing", author, genre));
        Book actualBook = libraryService.getBookById(addedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(addedBook);
    }

}