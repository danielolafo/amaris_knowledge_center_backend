package com.amaris.knowledgecenter.service;

import com.amaris.knowledgecenter.domain.Technology;
import com.amaris.knowledgecenter.domain.TechnologyVersion;
import com.amaris.knowledgecenter.dto.TechnologyRequest;
import com.amaris.knowledgecenter.dto.TechnologyResponse;
import com.amaris.knowledgecenter.repository.TechnologyRepository;
import com.amaris.knowledgecenter.repository.TechnologyVersionRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TechnologyService {

    private final TechnologyRepository technologies;
    private final TechnologyVersionRepository versions;

    public TechnologyService(TechnologyRepository technologies, TechnologyVersionRepository versions) {
        this.technologies = technologies;
        this.versions = versions;
    }

    public Mono<TechnologyResponse> findById(Long id) {
        return technologies.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException("Tecnología no encontrada"))))
                .flatMap(this::assemble);
    }

    public Flux<TechnologyResponse> findAll() {
        return technologies.findAll()
                .collectList()
                .flatMapMany(list -> {
                    if (list.isEmpty()) {
                        return Flux.empty();
                    }
                    List<Long> ids = list.stream().map(Technology::getId).toList();
                    return versions.findByTechnologyIdIn(ids).collectList()
                            .flatMapMany(versionList -> {
                                Map<Long, List<TechnologyResponse.VersionRef>> byTech = versionList.stream()
                                        .collect(Collectors.groupingBy(TechnologyVersion::getTechnologyId,
                                                Collectors.mapping(v -> new TechnologyResponse.VersionRef(v.getId(), v.getVersion()),
                                                        Collectors.toList())));
                                return Flux.fromIterable(list)
                                        .map(tech -> new TechnologyResponse(tech.getId(), tech.getName(),
                                                byTech.getOrDefault(tech.getId(), List.of())));
                            });
                });
    }

    public Mono<TechnologyResponse> create(TechnologyRequest request) {
        Technology technology = new Technology(null, request.name());
        return technologies.save(technology)
                .flatMap(saved -> createVersions(saved.getId(), request.versions())
                        .then(assemble(saved)));
    }

    private Mono<Void> createVersions(Long technologyId, List<String> versionLabels) {
        return versions.saveAll(versionLabels.stream()
                        .map(label -> new TechnologyVersion(null, technologyId, label))
                        .toList())
                .then();
    }

    private Mono<TechnologyResponse> assemble(Technology technology) {
        return versions.findByTechnologyId(technology.getId())
                .map(v -> new TechnologyResponse.VersionRef(v.getId(), v.getVersion()))
                .collectList()
                .map(refs -> new TechnologyResponse(technology.getId(), technology.getName(), refs));
    }
}