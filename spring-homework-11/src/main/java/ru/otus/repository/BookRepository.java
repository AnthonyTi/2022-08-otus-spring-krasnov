package ru.otus.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.otus.domain.Book;

public interface BookRepository extends ReactiveCrudRepository<Book, String> {


}
