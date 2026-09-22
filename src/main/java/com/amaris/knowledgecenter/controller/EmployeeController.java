package com.amaris.knowledgecenter.controller;

import com.amaris.knowledgecenter.dto.EmployeeRequest;
import com.amaris.knowledgecenter.dto.EmployeeResponse;
import com.amaris.knowledgecenter.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<EmployeeResponse> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<EmployeeResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EmployeeResponse> create(@Valid @RequestBody EmployeeRequest request) {
        return service.create(request);
    }
}