package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.Map;

@RestController
public class DemoController {
    private final DemoService service;
    private final ObjectMapper mapper;

    public DemoController(DemoService service, ObjectMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<Mono<String>> getString() {
        return ResponseEntity.ok(service.getString().contextWrite(getContext()));
    }

    @GetMapping("/error")
    public ResponseEntity<Mono<Void>> getError() {
        return ResponseEntity.badRequest().body(service.getError().contextWrite(getContext()));
    }

    private Context getContext() {
        try {
            return Context.of(
                    "mdc",
                    mapper.writeValueAsString(Map.of("mdc-value", "foo")));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
