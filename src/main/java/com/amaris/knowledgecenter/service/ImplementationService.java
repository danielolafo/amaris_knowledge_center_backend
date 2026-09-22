package com.amaris.knowledgecenter.service;

import com.amaris.knowledgecenter.domain.Employee;
import com.amaris.knowledgecenter.domain.Implementation;
import com.amaris.knowledgecenter.domain.ImplementationEmployee;
import com.amaris.knowledgecenter.domain.ImplementationTag;
import com.amaris.knowledgecenter.domain.ImplementationTechnologyVersion;
import com.amaris.knowledgecenter.domain.Technology;
import com.amaris.knowledgecenter.domain.TechnologyVersion;
import com.amaris.knowledgecenter.dto.ImplementationRequest;
import com.amaris.knowledgecenter.dto.ImplementationResponse;
import com.amaris.knowledgecenter.repository.EmployeeRepository;
import com.amaris.knowledgecenter.repository.ImplementationEmployeeRepository;
import com.amaris.knowledgecenter.repository.ImplementationRepository;
import com.amaris.knowledgecenter.repository.ImplementationTagRepository;
import com.amaris.knowledgecenter.repository.ImplementationTechnologyVersionRepository;
import com.amaris.knowledgecenter.repository.TechnologyRepository;
import com.amaris.knowledgecenter.repository.TechnologyVersionRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ImplementationService {

    private final ImplementationRepository implementations;
    private final ImplementationEmployeeRepository implementationEmployees;
    private final ImplementationTagRepository implementationTags;
    private final ImplementationTechnologyVersionRepository implementationTechVersions;
    private final EmployeeRepository employees;
    private final TechnologyRepository technologies;
    private final TechnologyVersionRepository technologyVersions;

    public ImplementationService(ImplementationRepository implementations,
                                 ImplementationEmployeeRepository implementationEmployees,
                                 ImplementationTagRepository implementationTags,
                                 ImplementationTechnologyVersionRepository implementationTechVersions,
                                 EmployeeRepository employees,
                                 TechnologyRepository technologies,
                                 TechnologyVersionRepository technologyVersions) {
        this.implementations = implementations;
        this.implementationEmployees = implementationEmployees;
        this.implementationTags = implementationTags;
        this.implementationTechVersions = implementationTechVersions;
        this.employees = employees;
        this.technologies = technologies;
        this.technologyVersions = technologyVersions;
    }

    public Mono<ImplementationResponse> findById(Long id) {
        return implementations.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException("Implementación no encontrada"))))
                .flatMap(this::assemble);
    }

    public Flux<ImplementationResponse> findAll() {
        return implementations.findAllByOrderByIdDesc().flatMap(this::assemble);
    }

    /**
     * Busca implementaciones por palabra clave (título, descripción o etiqueta).
     *
     * @param keyword Término a buscar; si está vacío devuelve todas.
     */
    public Flux<ImplementationResponse> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        return implementations.findByKeyword(keyword.trim())
                .concatWith(implementations.findByTag(keyword.trim()))
                .distinct(Implementation::getId)
                .flatMap(this::assemble);
    }

    public Mono<ImplementationResponse> create(ImplementationRequest request) {
        validateRequest(request);
        Implementation implementation = new Implementation(null, request.title(), request.description(),
                LocalDateTime.now());
        return implementations.save(implementation)
                .flatMap(saved -> {
                    Mono<Void> employeesOp = saveEmployees(saved.getId(), request.employeeIds());
                    Mono<Void> tagsOp = saveTags(saved.getId(), request.tags());
                    Mono<Void> techOp = saveTechVersions(saved.getId(), request.technologies());
                    return Mono.when(employeesOp, tagsOp, techOp).then(assemble(saved));
                });
    }

    private void validateRequest(ImplementationRequest request) {
        if (request.employeeIds() == null || request.employeeIds().isEmpty()) {
            throw new IllegalArgumentException("Debe indicarse al menos un empleado");
        }
        if (request.tags() == null || request.tags().isEmpty()) {
            throw new IllegalArgumentException("Debe indicarse al menos una etiqueta");
        }
        if (request.technologies() == null || request.technologies().isEmpty()) {
            throw new IllegalArgumentException("Debe indicarse al menos una tecnología usada");
        }
    }

    private Mono<Void> saveEmployees(Long implementationId, List<Long> employeeIds) {
        return implementationEmployees.saveAll(employeeIds.stream()
                        .map(id -> new ImplementationEmployee(null, implementationId, id))
                        .toList())
                .then();
    }

    private Mono<Void> saveTags(Long implementationId, List<String> tags) {
        return implementationTags.saveAll(tags.stream()
                        .map(tag -> new ImplementationTag(null, implementationId, tag.trim().toLowerCase()))
                        .toList())
                .then();
    }

    private Mono<Void> saveTechVersions(Long implementationId,
                                        List<ImplementationRequest.TechnologyVersionUsage> usages) {
        return implementationTechVersions.saveAll(usages.stream()
                        .map(u -> new ImplementationTechnologyVersion(null, implementationId,
                                u.technologyId(), u.versionId()))
                        .toList())
                .then();
    }

    private Mono<ImplementationResponse> assemble(Implementation implementation) {
        Long implId = implementation.getId();

        Mono<List<ImplementationEmployee>> employeeLinks =
                implementationEmployees.findByImplementationId(implId).collectList();
        Mono<List<ImplementationTag>> tagLinks =
                implementationTags.findByImplementationId(implId).collectList();
        Mono<List<ImplementationTechnologyVersion>> techLinks =
                implementationTechVersions.findByImplementationId(implId).collectList();

        return Mono.zip(employeeLinks, tagLinks, techLinks)
                .flatMap(tuple -> {
                    List<ImplementationEmployee> empLinks = tuple.getT1();
                    List<ImplementationTechnologyVersion> techLinkList = tuple.getT3();
                    List<Long> employeeIds = empLinks.stream()
                            .map(ImplementationEmployee::getEmployeeId).distinct().toList();
                    List<Long> technologyIds = techLinkList.stream()
                            .map(ImplementationTechnologyVersion::getTechnologyId).distinct().toList();
                    List<Long> versionIds = techLinkList.stream()
                            .map(ImplementationTechnologyVersion::getVersionId).distinct().toList();

                    Mono<Map<Long, Employee>> employeeById = employees.findAllById(employeeIds)
                            .collect(Collectors.toMap(Employee::getId, e -> e));
                    Mono<Map<Long, Technology>> techById = technologies.findAllById(technologyIds)
                            .collect(Collectors.toMap(Technology::getId, t -> t));
                    Mono<Map<Long, TechnologyVersion>> versionById =
                            technologyVersions.findAllById(versionIds)
                                    .collect(Collectors.toMap(TechnologyVersion::getId, v -> v));

                    return Mono.zip(employeeById, techById, versionById)
                            .map(detail -> toResponse(implementation, tuple.getT1(), tuple.getT2(),
                                    techLinkList, detail.getT1(), detail.getT2(), detail.getT3()));
                });
    }

    private ImplementationResponse toResponse(Implementation implementation,
                                              List<ImplementationEmployee> empLinks,
                                              List<ImplementationTag> tagLinks,
                                              List<ImplementationTechnologyVersion> techLinks,
                                              Map<Long, Employee> employeeById,
                                              Map<Long, Technology> techById,
                                              Map<Long, TechnologyVersion> versionById) {
        List<ImplementationResponse.EmployeeRef> employeeRefs = empLinks.stream()
                .map(link -> employeeById.get(link.getEmployeeId()))
                .filter(e -> e != null)
                .map(e -> new ImplementationResponse.EmployeeRef(e.getId(), e.getName()))
                .toList();

        List<String> tags = tagLinks.stream()
                .map(ImplementationTag::getTag)
                .toList();

        List<ImplementationResponse.TechnologyVersionUsed> used = techLinks.stream()
                .map(link -> {
                    Technology technology = techById.get(link.getTechnologyId());
                    TechnologyVersion version = versionById.get(link.getVersionId());
                    if (technology == null || version == null) {
                        return null;
                    }
                    return new ImplementationResponse.TechnologyVersionUsed(
                            technology.getId(), technology.getName(), version.getId(), version.getVersion());
                })
                .filter(u -> u != null)
                .toList();

        return new ImplementationResponse(implementation.getId(), implementation.getTitle(),
                implementation.getDescription(), implementation.getCreatedAt(), employeeRefs, tags, used);
    }
}