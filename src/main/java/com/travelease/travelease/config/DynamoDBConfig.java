package com.travelease.travelease.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import org.springframework.beans.factory.annotation.Value;


@Configuration
@EnableDynamoDBRepositories
public class DynamoDBConfig {

    @Bean
    public AmazonDynamoDB amazonDynamoDB(AWSCredentials AWSCredentials,
                                         @Value("${amazon.dynamodb.endpoint}") String dynamoDBURl) {
    
       AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard()
                                                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(dynamoDBURl, "eu-central-1"))
                                                .withCredentials(new AWSStaticCredentialsProvider(AWSCredentials));
        AmazonDynamoDB client = builder.build();
    
       return client;
    }

    @Bean
    public AWSCredentials awsCredentials(@Value("${amazon.aws.accesskey}")
                                        String accesskey,
                                        @Value("${amazon.aws.secretkey}")
                                        String secretkey) {
        return new BasicAWSCredentials(accesskey, secretkey);
    }

    
}
