package ru.otus.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA для работы с Genre")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    private static final Long FIRST_GENRE_ID = 1L;

    private static final Long SECOND_GENRE_ID = 3L;

    private static final int EXPECTED_NUMBER_OF_GENRES = 2;

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" Должен добавить жанр")
    @Test
    void insert() {
        Genre genre = new Genre("Жанр");
        val insertedGenre = genreRepositoryJpa.insert(genre);
        val expectedGenre = em.find(Genre.class, SECOND_GENRE_ID);
        assertThat(insertedGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName(" Должен загружать информацию о нужном жанре по его id")
    @Test
    void getById() {
        val genre = genreRepositoryJpa.getById(FIRST_GENRE_ID);
        val expectedGenre = em.find(Genre.class, FIRST_GENRE_ID);
        assertThat(genre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName(" Должен загружать список всех жанров")
    @Test
    void getAll() {
        val genres = genreRepositoryJpa.getAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES);
    }
}