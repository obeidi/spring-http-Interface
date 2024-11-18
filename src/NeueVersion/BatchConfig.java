package com.example.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ExcelDataRepository repository;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, ExcelDataRepository repository) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.repository = repository;
    }

    @Bean
    public Job importExcelJob(Step step) {
        return new JobBuilder("importExcelJob", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step step(ItemReader<ExcelData> reader,
                     ItemProcessor<ExcelData, ExcelData> processor,
                     ItemWriter<ExcelData> writer) {
        return new StepBuilder("step", jobRepository)
                .<ExcelData, ExcelData>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemReader<ExcelData> reader() {
        try {
            return new ExcelItemReader("path/to/your/excel/file.xlsx");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public ItemProcessor<ExcelData, ExcelData> processor() {
        return new ExcelDataProcessor();
    }

    @Bean
    public ItemWriter<ExcelData> writer() {
        return new ExcelDataWriter(repository);
    }
}
