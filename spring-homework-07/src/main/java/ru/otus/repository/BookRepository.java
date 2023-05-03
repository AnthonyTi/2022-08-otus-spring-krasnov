package ru.otus.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.List;

public interface BookRepository extends JpaRepository <Book, Long>{

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "author",
                    "genre"
            }
    )
    List<Book> findAll();

}
