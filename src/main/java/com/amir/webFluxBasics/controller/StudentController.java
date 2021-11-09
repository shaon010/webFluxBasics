package com.amir.webFluxBasics.controller;

import com.amir.webFluxBasics.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "student/{id}")
    public Mono<Map<String, Object>> getStudent(@PathVariable int id) {
        return studentService.getStudentData(id);
    }
}
