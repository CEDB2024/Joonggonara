package com.dbProject.joongo.global.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Getter
public class AmazonConfig {
    private AWSCredentials awsCredentials;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.path.product}")
    private String productPath;


    @Profile("local")
    @PostConstruct
    public void initLocal() {
        Dotenv dotenv = Dotenv.configure().directory("./src/main/java/com/dbProject/joongo").load();
        this.accessKey = dotenv.get("AWS_ACCESS_KEY_ID");
        this.secretKey = dotenv.get("AWS_SECRET_ACCESS_KEY");

        if (accessKey == null || secretKey == null || region == null) {
            throw new IllegalStateException("AWS credentials or region not found in .env file");
        }

//        System.out.println("AWS Access Key: " + accessKey);
//        System.out.println("AWS Secret Key: " + secretKey);
//        System.out.println("AWS Region: " + region);
//        System.out.println("AWS Bucket: " + bucket);
//        System.out.println("AWS Product Path: " + productPath);
        this.awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
    }

    @Profile("cloud")
    public void initCloud() {
        if (accessKey == null || secretKey == null || region == null) {
            throw new IllegalStateException("AWS credentials or region not found in .env file");
        }

        this.awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(awsCredentials);
    }


}
