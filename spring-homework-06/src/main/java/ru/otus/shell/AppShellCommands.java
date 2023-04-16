package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.repository.AuthorRepository;
import ru.otus.service.LibraryService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class AppShellCommands {

    private final LibraryService libraryService;

    private final AuthorRepository authorRepository;


    @ShellMethod(value = "Show authors", key = "sa")
    public String showAuthors () {
        List<Author> authorList = authorRepository.getAll();
        return String.format("List of authors: %s", authorList.toString());
    }

    @ShellMethod(value = "Add author", key = "addA")
    @Transactional
    public String addAuthor (@ShellOption()String name) {
        Author author = new Author(name);
        author = authorRepository.insert(author);
        return String.format("Author was added with id = %s", author.getId());
    }

    @ShellMethod(value = "Update author", key = "updA")
    @Transactional
    public String updAuthor (@ShellOption()Long id, @ShellOption()String name) {
        Author author = new Author(name);
        author.setId(id);
        authorRepository.update(author);
        return String.format("Author was updated with id = %s", id);
    }

    @ShellMethod(value = "Get books", key = "getAB")
    @Transactional
    public String getBooks () {
        List<Book> bookList = libraryService.getAllBooks();
        return String.format("List of books: %s", bookList.toString());
    }

    @ShellMethod(value = "Get book", key = "getB")
    @Transactional
    public String getBookById (@ShellOption() Long id) {
        Book book = libraryService.getBookById(id);
        return String.format("Book: %s", book);
    }

    @ShellMethod(value = "Get book's comments", key = "getBC")
    @Transactional
    public String getBookComments(@ShellOption() Long id) {
        List<Comment> comments = libraryService.getAllCommentsByBookId(id);
        return String.format("Comments: %s", comments);
    }

    @ShellMethod(value = "Add comment to book", key = "addBC")
    @Transactional
    public String addCommentByBookId(@ShellOption() Long id, @ShellOption()String text) {
        libraryService.addCommentByBookId(new Comment(text), id);
        List<Comment> comments = libraryService.getAllCommentsByBookId(id);
        return String.format("Comment was added. Comments: %s", comments);
    }

}
