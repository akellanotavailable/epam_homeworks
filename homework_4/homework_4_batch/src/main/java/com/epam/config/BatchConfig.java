package com.epam.config;


import com.epam.entity.User;
import com.epam.mapper.UserMapper;
import com.epam.service.EmailSender;
import com.epam.step.Processor;
import com.epam.step.Writer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@EnableBatchProcessing
@Configuration
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
@ComponentScan("com.epam")

public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final EmailSender emailSender;


    @Bean
    public JdbcCursorItemReader<User> itemReader(DataSource connection) {
        JdbcCursorItemReader<User> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(connection);
        reader.setSql("select * from user");
        reader.setRowMapper(new UserMapper());
        return reader;
    }

    @Bean
    public ItemProcessor<User, User> itemProcessor() {
        return new Processor();
    }

    @Bean
    public ItemWriter<User> itemWriter() {
        return new Writer(emailSender);
    }

    @Bean
    protected Step step1(JdbcCursorItemReader<User> itemReader) {
        return stepBuilderFactory.get("step1").<User, User>chunk(1)
                .reader(itemReader)
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean(name = "userJob")
    public Job userJob(Step step1) {
        return jobBuilderFactory.get("userJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }
}
