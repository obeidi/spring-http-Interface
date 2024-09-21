package com.one.testapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootApplication
public class TestAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestAppApplication.class, args);
    }

    @Bean
    CommandLineRunner run(HelloWorldClient helloWorldClient) {
        return args -> {
            String response = helloWorldClient.getHelloWorld();
            System.out.println("Response from Hello World Service: " + response);
        };
    }


    @Bean
    public HelloWorldClient helloWorldClient() {
        // WebClient instanziieren, um HTTP-Anfragen zu verwalten
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080") // URL zur ersten Anwendung
                .build();

        // Verwendung von WebClientAdapter anstelle der veralteten API
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();

        // Proxy-Factory verwendet die moderne WebClient-Implementierung
        return factory.createClient(HelloWorldClient.class);
    }

}
