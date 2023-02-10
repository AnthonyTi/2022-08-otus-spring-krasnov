package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с Book. Должно: ")
@JdbcTest
@Import({BookDaoImpl.class, AuthorDaoImpl.class, GenreDaoImpl.class})
class BookDaoImplTest {

    private static final String EXISTING_BOOK_NAME = "Book";
    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final Long EXISTING_GENRE_ID = 1L;

    @Autowired
    private BookDaoImpl bookDao;

    @Autowired
    private AuthorDaoImpl authorDao;

    @Autowired
    private GenreDaoImpl genreDao;

    @DisplayName("добавлять книгу в БД")
    @Test
    void insertTest() {
        Author author = authorDao.getById(EXISTING_AUTHOR_ID);
        Genre genre = genreDao.getById(EXISTING_GENRE_ID);
        Book book = new Book(null, "New book", author, genre);
        book = bookDao.insert(book);
        Book actualBook = bookDao.getById(book.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(book);
    }

    @DisplayName("удалять заданную книгу по ее id")
    @Test
    void deleteByIdTest() {
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXISTING_BOOK_ID);

        assertThatThrownBy(() -> bookDao.getById(EXISTING_BOOK_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("должен получить книгу по ID")
    @Test
    void getByIdTest() {
        Author author = authorDao.getById(EXISTING_AUTHOR_ID);
        Genre genre = genreDao.getById(EXISTING_GENRE_ID);
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, author, genre);
        Book actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("должен возвращать ожидаемый список книг")
    @Test
    void getAllTest() {
        Author author = authorDao.getById(EXISTING_AUTHOR_ID);
        Genre genre = genreDao.getById(EXISTING_GENRE_ID);
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_BOOK_NAME, author, genre);
        List<Book> actualBookList = bookDao.getAll();
        assertThat(actualBookList)
                .containsExactlyInAnyOrder(expectedBook);
    }
}