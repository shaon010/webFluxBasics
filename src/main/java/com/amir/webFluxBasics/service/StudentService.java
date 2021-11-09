package com.amir.webFluxBasics.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class StudentService {
    public Mono<String> getStudentData(int id) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://gorest.co.in/public/v1/")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
                .build();
        return webClient
                //todo allow post and get both
                .get()
                .uri("/users/" + id)
                .exchangeToMono(response -> {
                    if (response.statusCode()
                            .equals(HttpStatus.OK)) {
                        return response.bodyToMono(String.class);
                    } else if (response.statusCode()
                            .is4xxClientError()) {
                        return Mono.just("Error response");
                    } else {
                        return response.createException()
                                .flatMap(Mono::error);
                    }
                });
    }
}
