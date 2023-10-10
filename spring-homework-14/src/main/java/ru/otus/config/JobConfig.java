package ru.otus.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.domain.h2.BookH2;
import ru.otus.domain.mongo.Book;
import ru.otus.mapping.LibraryMapping;

import java.util.HashMap;

@RequiredArgsConstructor
@Configuration
public class JobConfig {
    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    public static final String JOB_NAME  = "importUserJob";

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    private final EntityManagerFactory entityManagerFactory;

    @StepScope
    @Bean
    public MongoItemReader<Book> reader(MongoTemplate mongoTemplate) {
        return new MongoItemReaderBuilder<Book>()
                .name("mongoBookItemReader")
                .collection("book")
                .template(mongoTemplate)
                .sorts(new HashMap<>())
                .targetType(Book.class)
                .jsonQuery("{}")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Book, BookH2> processor(LibraryMapping libraryMapping) {
        return libraryMapping::toBookH2;
    }

    @StepScope
    @Bean
    public JpaItemWriter<BookH2> writer() {
        return new JpaItemWriterBuilder<BookH2>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step transformBookStep(ItemReader<Book> reader,  ItemWriter<BookH2> writer,
                                  ItemProcessor<Book, BookH2> processor) {
        return new StepBuilder("transformBookStep", jobRepository)
                .<Book, BookH2>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .listener(new ItemWriteListener<BookH2>() {
                    @Override
                    public void beforeWrite(Chunk<? extends BookH2> items) {
                        for (BookH2 book: items.getItems()
                             ) {
                            logger.info(book.toString());
                        }
                    }
                })
                .build();
    }

    @Bean
    public Job importUserJob(Step transformBookStep) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(transformBookStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

}
