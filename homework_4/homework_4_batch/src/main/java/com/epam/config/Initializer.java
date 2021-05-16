package com.epam.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBatchProcessing
public class Initializer {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BatchConfig.class);

        JobLauncher jobLauncher = context.getBean(JobLauncher.class);

        Job job = context.getBean("userJob", Job.class);

        JobParameters jobParameters = new JobParametersBuilder().toJobParameters();

        JobExecution jobExecution = jobLauncher.run(job, jobParameters);

    }
}
