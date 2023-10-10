package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.config.JobConfig;

import java.util.Properties;


@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    private Long executionId;

    //http://localhost:8080/h2-console/


    @ShellMethod(value = "Запустить миграцию данных библиотеки", key = {"smj", "start migration job"})
    public String startMigrationJob() throws Exception {
        Properties jobParameters = new Properties();
        this.executionId = jobOperator.start(JobConfig.JOB_NAME, jobParameters);
        return String.format("Задача %s выполнена", jobOperator.getSummary(executionId));

    }

    @ShellMethod(value = "showInfo", key = "i")
    public void showInfo() {
        System.out.println(jobExplorer.getJobNames());
        System.out.println(jobExplorer.getLastJobInstance(JobConfig.JOB_NAME));
    }
}
