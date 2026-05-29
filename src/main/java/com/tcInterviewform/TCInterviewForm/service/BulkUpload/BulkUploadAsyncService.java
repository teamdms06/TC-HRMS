/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.service.BulkUpload;

import com.tcInterviewform.TCInterviewForm.dto.UploadProgress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author teamd
 */
@Service
public class BulkUploadAsyncService {

    private final Map<String, UploadProgress> progressMap = new ConcurrentHashMap<>();

    @Autowired
    private BulkUploadTransactionService transactionService;

    public UploadProgress getProgress(String jobId) {
        return progressMap.getOrDefault(
                jobId,
                new UploadProgress("UNKNOWN", 0, "Upload not found", 0, 0)
        );
    }

    @Async
    public void processUploadAsync(Path filePath, String documentType, String jobId) {

        progressMap.put(jobId,
                new UploadProgress("PROCESSING", 1, "Upload started", 0, 0));

        try {
            int count = transactionService.uploadDocumetWiseData(
                    filePath,
                    documentType,
                    jobId,
                    progressMap
            );

            progressMap.put(jobId,
                    new UploadProgress("COMPLETED", 100,
                            "Upload completed. Total uploaded: " + count,
                            count,
                            count));

        } catch (Exception e) {
            progressMap.put(jobId,
                    new UploadProgress("FAILED", 0,
                            "Upload failed: " + e.getMessage(),
                            0,
                            0));
        } finally {
            try {
                Files.deleteIfExists(filePath);
            } catch (Exception ignored) {
            ignored.printStackTrace();}
        }
    }
}
