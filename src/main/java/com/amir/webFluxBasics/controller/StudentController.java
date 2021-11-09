package com.amir.webFluxBasics.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@Controller
public class StudentController {

    @GetMapping(value = "student")
    public Mono<String> getStudent(@PathVariable(name = "id") int id) {
        return Mono.just("");
    }
}
