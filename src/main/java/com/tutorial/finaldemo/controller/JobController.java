package com.tutorial.finaldemo.controller;

import com.tutorial.finaldemo.entity.Product;
import com.tutorial.finaldemo.service.ProductService;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/add")
public class JobController {
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    ProductService productService;

    @Qualifier("importProductJob")
    @Autowired
    Job job;

    @PostMapping("/process")
    public ResponseEntity<String> processFile(@RequestBody String filePath) {
        try {

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("filePath", filePath)
                    .toJobParameters();

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
