package com.example.demo;

import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@Service
public class DemoService {
    Logger LOGGER = Logger.getLogger("DemoService");

    public Mono<String> getString() {
        LOGGER.info("Test log");

        return Mono.just("Hello, world!");
    }

    public Mono<Void> getError() {
        return Mono.error(new RuntimeException("Bad day"));
    }
}
