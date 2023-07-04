package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.domain.Comment;

@Data
@AllArgsConstructor
public class CommentDto {

    private String id;

    private String text;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText());
    }

    public Comment toDomainObject() {
        return new Comment(id, text);
    }


}
