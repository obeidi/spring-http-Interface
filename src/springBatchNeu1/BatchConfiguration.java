package com.example.exceltooraclebatch.config;

import com.example.exceltooraclebatch.job.ExcelReader;
import com.example.exceltooraclebatch.job.VertriebspartnerProcessor;
import com.example.exceltooraclebatch.job.VertriebspartnerWriter;
import com.example.exceltooraclebatch.model.Vertriebspartner;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.builder.StepBuilderHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public BatchConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job importJob(ExcelReader reader, VertriebspartnerProcessor processor, VertriebspartnerWriter writer) {
        return new JobBuilder("importJob", jobRepository)
                .start(step1(reader, processor, writer))
                .build();
    }

    @Bean
    public Step step1(ExcelReader reader, VertriebspartnerProcessor processor, VertriebspartnerWriter writer) {
        return new StepBuilder("step1", jobRepository)
                .<Vertriebspartner, Vertriebspartner>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
