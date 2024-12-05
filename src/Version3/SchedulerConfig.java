package com.example.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Value("${batch.job.scheduling-interval}")
    private long interval;

    public SchedulerConfig(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @Scheduled(fixedRateString = "${batch.job.scheduling-interval}")
    public void runBatchJob() throws Exception {
        jobLauncher.run(job, new org.springframework.batch.core.JobParameters());
    }
}
