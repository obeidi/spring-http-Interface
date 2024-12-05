package com.example.batch.config;

import com.example.batch.model.RPHVerdichtungVP;
import com.example.batch.reader.ExcelFileReader;
import com.example.batch.processor.ExcelRowProcessor;
import com.example.batch.writer.DatabaseWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public Job excelToDbJob(JobBuilderFactory jobBuilderFactory, Step step) {
        return jobBuilderFactory.get("excelToDbJob")
                .start(step)
                .build();
    }

    @Bean
    public Step step(StepBuilderFactory stepBuilderFactory, 
                     ExcelFileReader reader, 
                     ExcelRowProcessor processor, 
                     DatabaseWriter writer) {
        return stepBuilderFactory.get("step")
                .<RPHVerdichtungVP, RPHVerdichtungVP>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
