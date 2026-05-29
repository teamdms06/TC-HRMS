/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.controller.LetterIssue;

import com.tcInterviewform.TCInterviewForm.dto.UploadProgress;
import com.tcInterviewform.TCInterviewForm.service.BulkUpload.BulkUploadAsyncService;
import com.tcInterviewform.TCInterviewForm.util.ExcelService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
 
@Controller
public class ExcelController {

    @Autowired
    private ExcelService excelService;
    @Autowired
    private BulkUploadAsyncService bulkUploadAsyncService;

//    @PostMapping("/uploadOldIssueLetterData")
//   @ResponseBody
//public Map<String, String> upload(@RequestParam("file") MultipartFile file) {
//
//    Map<String, String> response = new HashMap<>();
//
//    try {
//        String msg = excelService.saveExcelData(file);
//
//        response.put("status", "success");
//        response.put("message", "File uploaded successfully!");
//        response.put("serviceMsg", msg);
//
//    } catch (Exception e) {
//
//        response.put("status", "error");
//        response.put("message", e.getMessage());
//    }
//
//    return response;
//}

@PostMapping("/upload-document-data")
@ResponseBody
public ResponseEntity<?> uploadDocumentBulk(
        @RequestParam("file") MultipartFile file,
        @RequestParam("documentType") String documentType) {

    try {
        int count = excelService.uploadDocumetWiseData(file, documentType);

        return ResponseEntity.ok(count + " records uploaded successfully");

    } catch (Exception e) {
        System.out.println("error"+e.getMessage());
        e.printStackTrace();
        return ResponseEntity.badRequest()
                .body("Upload failed: " + e.getMessage());
    }
}

//@PostMapping("/upload-document-bulk")
//@ResponseBody
//public Map<String, String> uploadDocumentData(@RequestParam("file") MultipartFile file,
//                                              @RequestParam("documentType") String documentType) throws Exception {
//
//    String jobId = UUID.randomUUID().toString();
//
//    Path tempFile = Files.createTempFile("hrms-upload-", ".xlsx");
//    file.transferTo(tempFile.toFile());
//
//    excelService.processUploadAsync(tempFile, documentType, jobId);
//
//    Map<String, String> res = new HashMap<>();
//    res.put("jobId", jobId);
//    return res;
//}
//
//@GetMapping("/upload-progress/{jobId}")
//@ResponseBody
//public UploadProgress getUploadProgress(@PathVariable String jobId) {
//    return excelService.getProgress(jobId);
//}
@PostMapping("/upload-document-bulk")
@ResponseBody
public Map<String, String> uploadDocumentData(@RequestParam("file") MultipartFile file,
                                              @RequestParam("documentType") String documentType) throws Exception {

    String jobId = UUID.randomUUID().toString();

    Path tempFile = Files.createTempFile("hrms-upload-", ".xlsx");
    file.transferTo(tempFile.toFile());

    bulkUploadAsyncService.processUploadAsync(tempFile, documentType, jobId);

    Map<String, String> response = new HashMap<>();
    response.put("jobId", jobId);

    return response;
}

@GetMapping("/upload-progress/{jobId}")
@ResponseBody
public UploadProgress uploadProgress(@PathVariable String jobId) {
    return bulkUploadAsyncService.getProgress(jobId);
}
}
