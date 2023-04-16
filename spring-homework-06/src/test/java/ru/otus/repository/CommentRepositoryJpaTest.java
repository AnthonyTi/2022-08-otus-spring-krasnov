package ru.otus.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA для работы с Comment")
@DataJpaTest
@Import({CommentRepositoryJpa.class, BookRepositoryJpa.class})
class CommentRepositoryJpaTest {

    private static final Long FIRST_COMMENT_ID = 1L;

    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;


    @Autowired
    private TestEntityManager em;

    @DisplayName(" Должен загружать комментарий по его id")
    @Test
    void getById() {
        val comment = commentRepositoryJpa.getById(FIRST_COMMENT_ID);
        val expectedComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(comment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

}