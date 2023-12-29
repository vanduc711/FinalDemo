package com.tutorial.finaldemo.config;

import com.tutorial.finaldemo.entity.User;
import com.tutorial.finaldemo.reponsitory.UserReponsitory;
import com.tutorial.finaldemo.service.EmailVerificationService;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.List;


@Configuration
@AllArgsConstructor
public class UserVerificationBatchConfig {

    @Autowired
    private final UserReponsitory userRepository;

    @Autowired
    private final EmailVerificationService emailVerificationService;

    @Bean
    @StepScope
    public ItemReader<User> readerUser() {
        LocalDateTime thirtySecondsAgo = LocalDateTime.now().minusSeconds(30);
        List<User> unverifiedUsers = userRepository.findByVerifiedFalseAndRegistrationTimeBefore(thirtySecondsAgo);
        unverifiedUsers.forEach(user ->
                System.out.println("User " + user.getId() + ": " + user.getEmail() + " - Registration Time: " + user.getRegistrationTime())
        );
        return new ListItemReader<>(unverifiedUsers);
    }

    @Bean
    public ItemProcessor<User, User> processorUser() {
        return user -> {
            System.out.println("processor");
            System.out.println("Processing Inactive User: " + user.getEmail());

            boolean verified = user.getVerified();
            System.out.println(verified);
            if (verified == true) {
            } else {
                // Gửi thông báo email
                System.out.println(verified);
                User sendEmailNotification = emailVerificationService.sendEmailNotification(
                        user.getEmail(),
                        "Tài khoản của bạn đã bị xóa",
                        "Vtài khoản của bạn không được xác minh vòng 30 giây."
                );
                System.out.println("Email đã được gửi thành công cho người dùng: " + sendEmailNotification);
                userRepository.delete(user);
                System.out.println("Tài khoản của người dùng đã bị xóa: " + user.getEmail());
            }
            return null;
        };
    }

    @Bean
    public ItemWriter<User> writerUser() {
        RepositoryItemWriter<User> writer = new RepositoryItemWriter<>();
        writer.setRepository(userRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    @Primary
    public Job sendEmail(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("sendEmail", jobRepository)
                .start(step1(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step1", jobRepository)
                .<User, User>chunk(10, transactionManager)
                .reader(readerUser())
                .processor(processorUser())
                .writer(writerUser())
                .build();
    }
}
