package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    private String id;

    private String userName;

    private String password;

    private Set<Role> roles;

}
