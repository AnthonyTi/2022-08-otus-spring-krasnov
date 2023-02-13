package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Dao для работы с Author. Должно: ")
@JdbcTest
@Import(AuthorDaoImpl.class)
class AuthorDaoImplTest {

    private static final String EXISTING_AUTHOR_NAME = "Ivan";
    private static final Long EXISTING_AUTHOR_ID = 1L;
    @Autowired
    private AuthorDaoImpl authorDao;

    @DisplayName("добавлять автора в БД")
    @Test
    void insertTest() {
        Author author = new Author(null, "Автор");
        author = authorDao.insert(author);
        Author actualAuthor = authorDao.getById(author.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(author);
    }


    @DisplayName("должен получить автора по ID")
    @Test
    void getByIdTest() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        Author actualAuthor = authorDao.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);

    }

    @DisplayName("должен возвращать ожидаемый список авторов")
    @Test
    void getAllTest() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        List<Author> actualAuthorList = authorDao.getAll();
        assertThat(actualAuthorList)
                .containsExactlyInAnyOrder(expectedAuthor);
    }

    @DisplayName("удалять заданного автора по его id")
    @Test
    void deleteByIdTest() {
        assertThatCode(() -> authorDao.getById(EXISTING_AUTHOR_ID))
                .doesNotThrowAnyException();

        authorDao.deleteById(EXISTING_AUTHOR_ID);

        assertThatThrownBy(() -> authorDao.getById(EXISTING_AUTHOR_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}