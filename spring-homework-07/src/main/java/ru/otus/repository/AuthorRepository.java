package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.otus.domain.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {



}
