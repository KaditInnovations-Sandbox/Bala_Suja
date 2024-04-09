package com.travelease.travelease;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
public class LambdaHandler implements RequestHandler<Object, Object> {

    @Override
    public Object handleRequest(Object input, Context context) {
        // Start Spring Boot application
        SpringApplication.run(LambdaHandler.class);
        return null;
    }

    public static void main(String[] args) {
        // Start Spring Boot application when running locally
        SpringApplication.run(LambdaHandler.class, args);
    }
}