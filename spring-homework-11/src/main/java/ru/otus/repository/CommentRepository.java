package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.domain.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {


}
