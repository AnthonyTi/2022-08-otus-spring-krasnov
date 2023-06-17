package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.service.LibraryService;


import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class AppShellCommands {

    private final LibraryService libraryService;

    @ShellMethod(value = "Show authors", key = "sa")
    public String showAuthors () {
        List<Author> authorList = libraryService.getAllAuthors();
        return String.format("List of authors: %s", authorList.toString());
    }

    @ShellMethod(value = "Show genres", key = "sg")
    public String showGenres () {
        List<Genre> genreList = libraryService.getAllGenres();
        return String.format("List of genres: %s", genreList.toString());
    }

    @ShellMethod(value = "Add author", key = "addA")
    public String addAuthor (@ShellOption()String name) {
        Author author = new Author(name);
        author = libraryService.addAuthor(author);
        return String.format("Author was added with id = %s", author.getId());
    }

    @ShellMethod(value = "Add genre", key = "addG")
    public String addGenre (@ShellOption()String name) {
        Genre genre = new Genre(name);
        genre = libraryService.addGenre(genre);
        return String.format("Genre was added with id = %s", genre.getId());
    }

    @ShellMethod(value = "Add book", key = "addB")
    public String addBook (@ShellOption()String name) {
        Book book = new Book(name);
        book = libraryService.addBook(book);
        return String.format("Genre was added with id = %s", book.getId());
    }

    @ShellMethod(value = "Get books", key = "getAB")
    public String getBooks () {
        List<Book> bookList = libraryService.getAllBooks();
        return String.format("Got %s books", bookList.size());
    }

    @ShellMethod(value = "Get book's comments", key = "getBC")
    public String getBookComments(@ShellOption() String id) {
        List<Comment> comments = libraryService.getAllCommentsByBookId(id);
        return String.format("Comments: %s", comments);
    }

    @ShellMethod(value = "Add comment to book", key = "addBC")
    public String addCommentByBookId(@ShellOption() String id, @ShellOption()String text) {
        libraryService.addCommentByBookId(new Comment(text), id);
        List<Comment> comments = libraryService.getAllCommentsByBookId(id);
        return String.format("Comment was added. Comments: %s", comments);
    }

    @ShellMethod(value = "Delete book by id", key = "delBID")
    public String deleteBookById(@ShellOption() String id) {
        libraryService.deleteBookById(id);
        return String.format("Book with id %s was deleted", id);
    }

}
