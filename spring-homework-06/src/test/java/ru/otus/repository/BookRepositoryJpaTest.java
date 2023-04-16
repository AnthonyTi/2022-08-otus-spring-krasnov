package ru.otus.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA для работы с Book")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    private static final Long FIRST_BOOK_ID = 1L;

    private static final Long EXPECTED_BOOK_ID = 3L;

    private static final int EXPECTED_AMOUNT_OF_BOOKS = 2;

    private static final int EXPECTED_AMOUNT_OF_COMMENTS = 3;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Должен добавить книгу")
    @Test
    void insert() {
        Book book = new Book("Новая книга");
        val insertedBook = bookRepositoryJpa.insert(book);
        val expectedBook = em.find(Book.class, EXPECTED_BOOK_ID);
        assertThat(insertedBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Должен добавить комментарий к книге")
    @Test
    void addComment() {
        val book = bookRepositoryJpa.getById(FIRST_BOOK_ID);
        bookRepositoryJpa.addCommentToBook(new Comment("Тестовый комментарий"), book.getId());
        val bookWithNewComment = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(bookWithNewComment.getComments()).hasSize(EXPECTED_AMOUNT_OF_COMMENTS);
    }

    @DisplayName("Должен вернуть книгу по id")
    @Test
    void getById() {
        val firstBook = bookRepositoryJpa.getById(FIRST_BOOK_ID);
        val expectedFirstBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(firstBook).usingRecursiveComparison().isEqualTo(expectedFirstBook);
    }

    @DisplayName("Должен вернуть книги. Проверить что они есть и их количество равно 2")
    @Test
    void getAll() {
        val books = bookRepositoryJpa.getAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_AMOUNT_OF_BOOKS);
    }
}