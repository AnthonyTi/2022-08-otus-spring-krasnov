package ru.otus.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA для работы с Author")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    private static final Long FIRST_AUTHOR_ID = 1L;

    private static final Long SECOND_AUTHOR_ID = 3L;

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 2;

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" Должен добавить автора")
    @Test
    void insert() {
        Author author = new Author("Автор");
        val insertedGenre = authorRepositoryJpa.insert(author);
        val expectedGenre = em.find(Author.class, SECOND_AUTHOR_ID);
        assertThat(insertedGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName(" Должен загружать информацию о нужном авторе по его id")
    @Test
    void getById() {
        val author = authorRepositoryJpa.getById(FIRST_AUTHOR_ID);
        val expectedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(author).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName(" Должен загружать список всех авторов")
    @Test
    void getAll() {
        val authors = authorRepositoryJpa.getAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS);
    }
}