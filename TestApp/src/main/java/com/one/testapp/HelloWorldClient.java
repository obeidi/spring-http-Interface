    package com.one.testapp;

    import org.springframework.stereotype.Component;
    import org.springframework.web.service.annotation.GetExchange;

@Component
public interface HelloWorldClient {

    @GetExchange("/hello")
    String getHelloWorld();
}
