package com.amaris.knowledgecenter.controller;

import com.amaris.knowledgecenter.dto.ImplementationRequest;
import com.amaris.knowledgecenter.dto.ImplementationResponse;
import com.amaris.knowledgecenter.service.ImplementationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/implementations")
public class ImplementationController {

    private final ImplementationService service;

    public ImplementationController(ImplementationService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<ImplementationResponse> list(@RequestParam(required = false) String q) {
        return service.search(q);
    }

    @GetMapping("/{id}")
    public Mono<ImplementationResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ImplementationResponse> create(@Valid @RequestBody ImplementationRequest request) {
        return service.create(request);
    }
}