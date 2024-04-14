package com.travelease.travelease;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TraveleaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(TraveleaseApplication.class, args);
	}

	@Bean
	JsonPlaceholderService jsonPlaceholderService() {
	    WebClient client = WebClient.builder()
	            .baseUrl("https://jsonplaceholder.typicode.com")
	            .build();
	    HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(client)).build();
	    return factory.createClient(JsonPlaceholderService.class);
	}

}
