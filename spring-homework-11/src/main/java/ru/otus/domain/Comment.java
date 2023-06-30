package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    private String id;

    private String text;

    public Comment(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "\nComment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

}
