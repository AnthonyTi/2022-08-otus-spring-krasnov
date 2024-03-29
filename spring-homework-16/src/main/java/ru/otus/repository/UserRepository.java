package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUserName(String userName);

}
