package com.restaurant.management.client;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class AmazonClient {
    private AmazonS3 s3client;

    @Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.secretAccessKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.EU_WEST_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
    }
}