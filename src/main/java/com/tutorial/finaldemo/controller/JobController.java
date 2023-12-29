package com.tutorial.finaldemo.controller;

import com.tutorial.finaldemo.entity.Product;
import com.tutorial.finaldemo.service.ProductService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/process")
public class JobController {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    ProductService productService;

    @Qualifier("importProductJob")
    @Autowired
    Job job;

    @PostMapping("/import")
    public ResponseEntity<String> processFile() {
        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong("start", System.currentTimeMillis()).toJobParameters();
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            return ResponseEntity.ok("File processing started. Job Id: " + jobExecution.getId());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file.");
        }
    }

    @GetMapping("/export")
    public void exportToCsv() {
        List<Product> products = productService.getAllProduct();

        try (FileWriter writer = new FileWriter("src/main/resources/file.csv")) {
            // Write CSV header
            writer.append("ID,Name,Price,CategoryId\n");

            // Write CSV data
            for (Product product : products) {
                writer.append(String.format("%d,%s,%s,%s\n", product.getId(), product.getName(), product.getPrice(), product.getCategory().getId()));
            }

            System.out.println("Data exported to CSV successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

}
