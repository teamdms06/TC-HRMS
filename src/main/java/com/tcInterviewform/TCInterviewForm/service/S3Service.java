/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcInterviewform.TCInterviewForm.service;

import com.tcInterviewform.TCInterviewForm.repository.ResumeMasterRepository;
import java.io.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.core.sync.RequestBody;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

/**
 *
 * @author teamd
 */
@Service
@RequiredArgsConstructor
public class S3Service {

//    private final String region = "ap-south-1";
//    private final String bucketName = "hrms-interview-cv";
    @Value("${aws.s3.bucketName}")
    private String bucketName;
    private final S3Client s3Client;
    private final S3Presigner presigner;


    public String uploadFile(MultipartFile file) {
        try {
            // Convert MultipartFile to Path (Temp File)
            Path tempFile = Files.createTempFile(file.getOriginalFilename(), null);
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            // Upload file to S3--
            String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uniqueFileName)
                    .contentType(file.getContentType())
                    .build(),
                    RequestBody.fromFile(tempFile));

            // Return the file URL
            return "https://" + bucketName + ".s3.amazonaws.com/" + uniqueFileName;

        } catch (IOException | S3Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String uploadEmployeeDocment(String empId,String documentType,MultipartFile file) {
        try {
            // Convert MultipartFile to Path (Temp File)
            Path tempFile = Files.createTempFile(file.getOriginalFilename(), null);
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            // Upload file to S3--
            String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            String key = "employee_documents/"+empId+ "/" + documentType.toLowerCase().replace(" ", "_")+"/" + uniqueFileName;
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build(),
                    RequestBody.fromFile(tempFile));

            // Return the file URL
            return key;

        } catch (IOException | S3Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//document store on s3

    public String uploadPdfToS3(
            ByteArrayOutputStream pdfStream,
            String documentType,
            String fileName) throws Exception {

        String key = "hrms-documents/" + documentType + "/" + fileName;

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("application/pdf")
                .build();

        s3Client.putObject(
                request,
                RequestBody.fromBytes(pdfStream.toByteArray())
        );

        return key;
    }

    public String generatePresignedUrl(String key) {

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest
                = GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofMinutes(10))
                        .getObjectRequest(getObjectRequest)
                        .build();

        PresignedGetObjectRequest presignedRequest
                = presigner.presignGetObject(presignRequest);

        return presignedRequest.url().toString();
    }

    public byte[] getFileFromS3(String s3Key) {

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build();

        ResponseBytes<GetObjectResponse> objectBytes
                = s3Client.getObjectAsBytes(getObjectRequest);

        return objectBytes.asByteArray();
    }

}
