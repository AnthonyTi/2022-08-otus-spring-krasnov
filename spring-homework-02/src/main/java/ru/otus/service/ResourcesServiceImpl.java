package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.SpringApplication;
import ru.otus.domain.Questionnaire;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class ResourcesServiceImpl implements SourceService {
    private final String resourcePath;

    public ResourcesServiceImpl(final String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public List<Questionnaire> getQuestions() {
        List<Questionnaire> result = new ArrayList<>();
        InputStream is = SpringApplication.class.getClassLoader().getResourceAsStream(this.resourcePath);
        Scanner s = new Scanner(is, StandardCharsets.UTF_8).useDelimiter("\n");
        while (s.hasNext()) {
            String[] strArr = s.next().split(";");
            String question = strArr.length > 0 ? strArr[0] : "";
            String answer = strArr.length == 2 ? strArr[1] : "";
            Questionnaire questionnaire = new Questionnaire(question, answer);
            result.add(questionnaire);
        }
        return result;
    }
}
