package ru.otus.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.mongo.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {


}
