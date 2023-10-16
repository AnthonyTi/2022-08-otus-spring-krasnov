package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
}
