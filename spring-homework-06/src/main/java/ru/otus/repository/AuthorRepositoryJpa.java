package ru.otus.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author insert(Author author) {
        if (author.getId() == null) {
            em.persist(author);
            return author;
        }
        return em.merge(author);
    }

    @Override
    public void deleteAuthor(Author author) {
        em.remove(author);
    }

    @Override
    public Author update(Author author) {
        return em.merge(author);
    }

    @Override
    public Author getById(Long id) {
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> getAll() {
        var query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }
}
