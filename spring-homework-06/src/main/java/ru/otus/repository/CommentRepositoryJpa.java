package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void deleteById(Long id) {
        Query query = em.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void update(Comment comment) {
        Query query = em.createQuery("update Comment c " +
                "set c.text = :text " +
                "where c.id = :id");
        query.setParameter("text", comment.getText());
        query.setParameter("id", comment.getId());
        query.executeUpdate();
    }

    @Override
    public Comment getById(Long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> getAllByBookId(Book book) {
        var query = em.createQuery("select b.comments from Book b where b.id = :bookId", Comment.class);
        query.setParameter("bookId", book.getId());
        return query.getResultList();
    }
}
