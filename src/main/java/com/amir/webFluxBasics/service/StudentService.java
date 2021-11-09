package com.amir.webFluxBasics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

@Service
public class StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);

    public Mono<Map<String, Object>> getStudentData(int id) {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://gorest.co.in/public/v1/")
                //.defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        return webClient
                .get()
                .uri("/users/" + id)
                .exchangeToMono(response -> {
                    if (response.statusCode()
                            .equals(HttpStatus.OK)) {
                        return response.bodyToMono(String.class);
                    } else {
                        //todo build the response with proper return status
                        return response.createException()
                                .flatMap(Mono::error);
                    }
                })
                .flatMap(responseString -> {
                    try {
                        return convertDataToJson(responseString);
                    } catch (JsonProcessingException e) {
                        LOGGER.error("Json Processing error", e);
                        return Mono.error(e);
                    }
                })
                .onErrorResume(throwable -> {
                    LOGGER.error("We got some error", throwable);
                    return Mono.error(throwable);
                });
    }

    public Mono<Map<String, Object>> convertDataToJson(String studentJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(studentJson, Map.class);
        return Mono.just(map);
    }
}
