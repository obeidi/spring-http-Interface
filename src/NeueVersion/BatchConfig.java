package com.example.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ExcelDataRepository repository;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, ExcelDataRepository repository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.repository = repository;
    }

    @Bean
    public Job importExcelJob() {
        return jobBuilderFactory.get("importExcelJob")
                .incrementer(new RunIdIncrementer())
                .flow(step())
                .end()
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .<ExcelData, ExcelData>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ExcelItemReader reader() {
        try {
            return new ExcelItemReader("path/to/your/excel/file.xlsx");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public ExcelDataProcessor processor() {
        return new ExcelDataProcessor();
    }

    @Bean
    public ExcelDataWriter writer() {
        return new ExcelDataWriter(repository);
    }
}
