package net.dzakirin.customer_transaction_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.dzakirin.customer_transaction_service.util.TxtToCsvConverter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/batch")
@RequiredArgsConstructor
@Tag(name = "Batch Processing", description = "Batch job execution APIs")
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Operation(summary = "Start Batch Job", description = "Triggers the batch job to process transactions.")
    @GetMapping("/start")
    public ResponseEntity<String> startBatchJob() {
        try {
            TxtToCsvConverter.convertTxtToCsv();

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startTime", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);
            return ResponseEntity.ok("Batch job started successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to start batch job: " + e.getMessage());
        }
    }
}
