package com.epam.edp.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of("eu-central-1")) // Change if your S3 bucket is in a different region
                .credentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
                .build();
    }
}
