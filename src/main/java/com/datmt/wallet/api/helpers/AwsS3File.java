package com.datmt.wallet.api.helpers;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

public class AwsS3File {

    private static final String BUCKET = System.getenv("S3_BUCKET");
    private static final String REGION = System.getenv("S3_REGION");
    private static final String ACCESS_KEY = System.getenv("S3_ACCESS_KEY");
    private static final String ENDPOINT = System.getenv("S3_ENDPOINT");
    private static final String SECRET_ACCESS_KEY = System.getenv(
            "S3_SECRET_ACCESS_KEY"
    );
    private final AmazonS3 s3Client;

    public AwsS3File() {
        AWSCredentialsProvider cred = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(ACCESS_KEY, SECRET_ACCESS_KEY)
        );
        s3Client =
                AmazonS3ClientBuilder
                        .standard()
                        .withEndpointConfiguration(
                                new AwsClientBuilder.EndpointConfiguration(ENDPOINT, REGION)
                        )
                        .withCredentials(cred)
                        .build();
    }

    public PutObjectResult uploadObjectPrivate(File file, String spaceFilePath) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(
                BUCKET,
                spaceFilePath,
                file
        );
        return s3Client.putObject(putObjectRequest);
    }

    public void deleteObject(String objectKey) {
        var deleteRequest = new DeleteObjectRequest(BUCKET, objectKey);
        s3Client.deleteObject(deleteRequest);
    }

    public URL generateDownloadURL(String objectKey, int expirationInMinutes) {
        return s3Client.generatePresignedUrl(
                BUCKET,
                objectKey,
                new Date(System.currentTimeMillis() + expirationInMinutes * 60000L),
                HttpMethod.GET
        );
    }

    public URL generateDownloadURL(String objectKey) {
        return generateDownloadURL(objectKey, 180);
    }

    public byte[] getObjectByte(String objectKey) throws IOException {
        return s3Client.getObject(BUCKET, objectKey).getObjectContent().readAllBytes();
    }
}
