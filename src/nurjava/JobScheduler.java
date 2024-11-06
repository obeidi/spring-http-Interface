import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@EnableScheduling
public class JobScheduler {

    private static final Logger logger = Logger.getLogger(JobScheduler.class.getName());

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Scheduled(fixedDelay = 300000) // Alle 5 Minuten
    public void runJob() {
        try {
            JobParameters params = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, params);
            logger.info("Batch-Job erfolgreich ausgeführt");
        } catch (Exception e) {
            logger.severe("Fehler beim Ausführen des Batch-Jobs: " + e.getMessage());
        }
    }
}
