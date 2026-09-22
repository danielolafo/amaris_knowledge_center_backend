package com.amaris.knowledgecenter.controller;

import com.amaris.knowledgecenter.dto.TechnologyRequest;
import com.amaris.knowledgecenter.dto.TechnologyResponse;
import com.amaris.knowledgecenter.service.TechnologyService;
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
@RequestMapping("/api/technologies")
public class TechnologyController {

    private final TechnologyService service;

    public TechnologyController(TechnologyService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<TechnologyResponse> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Mono<TechnologyResponse> findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TechnologyResponse> create(@Valid @RequestBody TechnologyRequest request) {
        return service.create(request);
    }
}