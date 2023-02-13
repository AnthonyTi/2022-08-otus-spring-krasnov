package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.dao.BookDaoImpl;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LibraryServiceImplTest {

    @MockBean
    private BookDaoImpl bookDao;

    private LibraryService libraryService;

    private final Author author = new Author(1L, "Author");
    private final Genre genre = new Genre(1L, "Genre");

    @BeforeEach
    void init() {
        libraryService = new LibraryServiceImpl(bookDao);
        Book book = new Book(1L, "Book", author, genre);
        Mockito.when(bookDao.getAll()).thenReturn(List.of(book));
        Mockito.when(bookDao.getById(1L)).thenReturn(book);
    }

    @DisplayName("Должен вернуть книгу")
    @Test
    void getBookById() {
        Book expectedBook = new Book(1L, "Book", author, genre);
        Book actualBook = libraryService.getBookById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Должен проверить список книг")
    @Test
    void getAllBook() {
        Book expectedBook = new Book(1L, "Book", author, genre);
        List<Book> actualBookList = libraryService.getAllBooks();
        assertThat(actualBookList)
                .containsExactlyInAnyOrder(expectedBook);
    }

    @DisplayName("Должен проверить добавление книги в библиотеку")
    @Test
    void addBook() {
        Book book = new Book(1L, "Book", author, genre);
        libraryService.addBook(book);
        Book actualBook = libraryService.getBookById(book.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(book);
    }

}