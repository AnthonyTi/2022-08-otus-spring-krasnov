package ru.otus.domain.h2;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@NamedEntityGraph(name = "book-entity-graph",
        attributeNodes = {@NamedAttributeNode("author"), @NamedAttributeNode("genre")})
public class BookH2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private AuthorH2 author;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private GenreH2 genre;

    @OneToMany(targetEntity = CommentH2.class, cascade = CascadeType.ALL, mappedBy = "book")
    private List<CommentH2> comments;

    public BookH2() {
    }

    public BookH2(String name, AuthorH2 author, GenreH2 genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public BookH2(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorH2 getAuthor() {
        return author;
    }

    public void setAuthor(AuthorH2 author) {
        this.author = author;
    }

    public GenreH2 getGenre() {
        return genre;
    }

    public void setGenre(GenreH2 genre) {
        this.genre = genre;
    }

    public List<CommentH2> getComments() {
        if (comments == null) {
            return new ArrayList<>();
        }
        return comments;
    }

    public void setComments(List<CommentH2> comments) {
        if (comments != null) {
            comments.forEach(comment -> comment.setBook(this));
        }
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "\nBook{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", \n\tauthor=" + author +
                ", \n\tgenre=" + genre +
                "}\n";
    }

}
