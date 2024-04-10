package com.travelease.travelease;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.internal.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LambdaHandler implements RequestHandler<AwsProxyRequest, AwsProxyResponse> {
    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    private static AnnotationConfigApplicationContext appContext;

    static {
        try {
            appContext = new AnnotationConfigApplicationContext(Application.class);
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(appContext);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Unable to initialize Spring Boot application context", ex);
        }
    }

    @Override
    public AwsProxyResponse handleRequest(AwsProxyRequest input, Context context) {
        return handler.proxy(input, context);
    }
}
