package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.domain.Author;
import ru.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с Genre. Должно: ")
@JdbcTest
@Import(GenreDaoImpl.class)
class GenreDaoImplTest {

    private static final String EXISTING_GENRE_NAME = "Science";
    private static final Long EXISTING_GENRE_ID = 1L;
    @Autowired
    private GenreDaoImpl genreDao;

    @DisplayName("добавлять жанр в БД")
    @Test
    void insertTest() {
        Genre genre = new Genre(null, "Автор");
        genre = genreDao.insert(genre);
        Genre actualAuthor = genreDao.getById(genre.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(genre);
    }


    @DisplayName("должен получить жанр по ID")
    @Test
    void getByIdTest() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = genreDao.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);

    }

    @DisplayName("должен возвращать ожидаемый список жанров")
    @Test
    void getAllTest() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        List<Genre> actualGenreList = genreDao.getAll();
        assertThat(actualGenreList)
                .containsExactlyInAnyOrder(expectedGenre);
    }

    @DisplayName("удалять заданный жанр по его id")
    @Test
    void deleteByIdTest() {
        assertThatCode(() -> genreDao.getById(EXISTING_GENRE_ID))
                .doesNotThrowAnyException();

        genreDao.deleteById(EXISTING_GENRE_ID);

        assertThatThrownBy(() -> genreDao.getById(EXISTING_GENRE_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}