package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.Application;
import ru.otus.config.AppProps;
import ru.otus.domain.Questionnaire;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class ResourcesServiceImpl implements SourceService {

    private final AppProps appProps;

    private final String EXT = ".csv";

    @Override
    public List<Questionnaire> getQuestions() {
        List<Questionnaire> result = new ArrayList<>();
        InputStream is = Application.class.getClassLoader()
                .getResourceAsStream(appProps.getResourcePath() + "_" + appProps.getLocale().getLanguage() + EXT);
        Scanner s = new Scanner(is, StandardCharsets.UTF_8).useDelimiter("\n");
        while (s.hasNext()) {
            result.add(parseStringToQuestion(s.next()));
        }
        return result;
    }

    private Questionnaire parseStringToQuestion(final String str) {
        String[] strArr = str.split(";");
        String question = strArr.length > 0 ? strArr[0] : "";
        String answer = strArr.length == 2 ? strArr[1] : "";
        return new Questionnaire(question, answer);
    }
}
