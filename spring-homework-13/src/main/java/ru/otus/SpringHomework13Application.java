package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableMongock
public class SpringHomework13Application {


    public static void main(String[] args) {
        SpringApplication.run(SpringHomework13Application.class);
        System.out.printf("Чтобы перейти на страницу сайта открывай: %n%s%n",
                "http://localhost:8080/books");

    }

}
