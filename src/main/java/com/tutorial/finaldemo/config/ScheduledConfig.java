package com.tutorial.finaldemo.config;

import com.tutorial.finaldemo.config.process.ContractProcessor;
import com.tutorial.finaldemo.config.reader.ContractReader;
import com.tutorial.finaldemo.config.writer.ContractWriter;
import com.tutorial.finaldemo.dto.ContractResponse;
import com.tutorial.finaldemo.reponsitory.ContractReponsitory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.UUID;

@Configuration
@EnableScheduling
@Slf4j
public class ScheduledConfig {

    @Autowired
    private ContractConfig contractConfig;

    @Autowired
    private ContractReponsitory contractReponsitory;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("job-scan")
    private Job job;

    @Scheduled(fixedRate = 20000)
    public void runJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("id", String.valueOf("Verification " + UUID.randomUUID())).toJobParameters();
        jobLauncher.run(job, jobParameters);
    }

    @Bean(name = "job-scan")
    public Job jobScanBlock(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("jobScanBlock", jobRepository).start(stepScan(jobRepository, transactionManager))
                .preventRestart().build();
    }

    @Bean
    public Step stepScan(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("stepScan", jobRepository).<ContractResponse, ContractResponse>chunk(1, transactionManager)
                .reader(new ContractReader(contractConfig)).processor(new ContractProcessor()).writer(new ContractWriter(contractReponsitory))
                .build();
    }
}
