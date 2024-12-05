package com.example.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job job;

    public BatchController(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    @PostMapping("/start")
    public String startBatch() throws Exception {
        jobLauncher.run(job, new org.springframework.batch.core.JobParameters());
        return "Batch started!";
    }

    @PostMapping("/stop")
    public String stopBatch() {
        // Implementiere Stopp-Logik
        return "Batch stopped!";
    }
}
