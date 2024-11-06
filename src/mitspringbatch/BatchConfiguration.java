import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Bean
    public Job importJob(JobBuilderFactory jobs, Step step1) {
        return jobs.get("importJob")
                   .start(step1)
                   .build();
    }

    @Bean
    public Step step1(StepBuilderFactory steps, ExcelReader reader, 
                      VertriebspartnerProcessor processor, VertriebspartnerWriter writer) {
        return steps.get("step1")
                    .<Vertriebspartner, Vertriebspartner>chunk(10)
                    .reader(reader)
                    .processor(processor)
                    .writer(writer)
                    .build();
    }
}
