package com.example.demo.controller; // Update if your project uses a different package

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import software.amazon.awssdk.core.sync.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class S3Controller {

    private final S3Client s3Client;

    public S3Controller(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> getFileFromS3() {
        String bucket = "cmtr-647f9884";
        String key = "data.txt";

        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            ResponseInputStream<?> s3Object = s3Client.getObject(request);

            String content = new BufferedReader(new InputStreamReader(s3Object))
                    .lines()
                    .collect(Collectors.joining("\n"));

            Map<String, String> response = new HashMap<>();
            response.put("content", content);

            return ResponseEntity.ok(response);

        } catch (S3Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to fetch S3 object: " + e.awsErrorDetails().errorMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}
