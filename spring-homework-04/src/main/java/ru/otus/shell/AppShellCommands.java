package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.config.AppProps;
import ru.otus.domain.User;
import ru.otus.service.QuestionnaireService;

@ShellComponent
@RequiredArgsConstructor
public class AppShellCommands {

    private final QuestionnaireService questionnaireService;

    private final MessageSource messageSource;

    private final AppProps appProps;

    private User user;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login (@ShellOption() String firstName, @ShellOption String lastName) {
        this.user = new User(firstName, lastName, 0);
        return String.format(
                messageSource.getMessage("shell.welcome",
                        new String[]{firstName, lastName},
                        appProps.getLocale()));
    }

    @ShellMethod(value = "Start test", key = {"s", "start"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public void startTest() {
        questionnaireService.startNewQuestionnaire(user);
    }

    private Availability isPublishEventCommandAvailable() {
        return user == null
                ? Availability.unavailable(
                        messageSource.getMessage("error.login", null, appProps.getLocale()))
                : Availability.available();
    }


}
