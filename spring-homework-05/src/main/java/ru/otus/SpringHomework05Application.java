package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.h2.tools.Console;

@SpringBootApplication
public class SpringHomework05Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringHomework05Application.class, args);

		Console.main(args);
	}

}
