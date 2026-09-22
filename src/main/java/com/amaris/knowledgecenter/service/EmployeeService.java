package com.amaris.knowledgecenter.service;

import com.amaris.knowledgecenter.domain.Employee;
import com.amaris.knowledgecenter.domain.EmployeeSkill;
import com.amaris.knowledgecenter.domain.EmployeeTechnology;
import com.amaris.knowledgecenter.dto.EmployeeRequest;
import com.amaris.knowledgecenter.dto.EmployeeResponse;
import com.amaris.knowledgecenter.repository.EmployeeRepository;
import com.amaris.knowledgecenter.repository.EmployeeSkillRepository;
import com.amaris.knowledgecenter.repository.EmployeeTechnologyRepository;
import com.amaris.knowledgecenter.repository.TechnologyRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeService {

    private final EmployeeRepository employees;
    private final EmployeeSkillRepository skills;
    private final EmployeeTechnologyRepository employeeTechnologies;
    private final TechnologyRepository technologies;

    public EmployeeService(EmployeeRepository employees,
                           EmployeeSkillRepository skills,
                           EmployeeTechnologyRepository employeeTechnologies,
                           TechnologyRepository technologies) {
        this.employees = employees;
        this.skills = skills;
        this.employeeTechnologies = employeeTechnologies;
        this.technologies = technologies;
    }

    public Mono<EmployeeResponse> findById(Long id) {
        return employees.findById(id)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException("Empleado no encontrado"))))
                .flatMap(this::assemble);
    }

    public Flux<EmployeeResponse> findAll() {
        return employees.findAll()
                .collectList()
                .flatMapMany(list -> {
                    if (list.isEmpty()) {
                        return Flux.empty();
                    }
                    List<Long> ids = list.stream().map(Employee::getId).toList();
                    return skills.findByEmployeeIdIn(ids).collectList()
                            .zipWith(employeeTechnologies.findByEmployeeIdIn(ids).collectList())
                            .zipWith(technologies.findAll().collectList())
                            .flatMapMany(middle -> {
                                Map<Long, List<String>> skillsByEmployee = middle.getT1().getT1().stream()
                                        .collect(Collectors.groupingBy(EmployeeSkill::getEmployeeId,
                                                Collectors.mapping(EmployeeSkill::getSkill, Collectors.toList())));
                                Map<Long, List<Long>> techIdsByEmployee = middle.getT1().getT2().stream()
                                        .collect(Collectors.groupingBy(EmployeeTechnology::getEmployeeId,
                                                Collectors.mapping(EmployeeTechnology::getTechnologyId, Collectors.toList())));
                                Map<Long, String> techNameById = middle.getT2().stream()
                                        .collect(Collectors.toMap(t -> t.getId(), t -> t.getName()));
                                return Flux.fromIterable(list)
                                        .map(emp -> toResponse(emp,
                                                skillsByEmployee.getOrDefault(emp.getId(), List.of()),
                                                techIdsByEmployee.getOrDefault(emp.getId(), List.of()),
                                                techNameById));
                            });
                });
    }

    public Mono<EmployeeResponse> create(EmployeeRequest request) {
        Employee employee = new Employee(null, request.name(), request.email(), request.position());
        return employees.save(employee)
                .flatMap(saved -> saveSkills(saved.getId(), request.skills())
                        .then(saveTechnologies(saved.getId(), request.technologyIds()))
                        .then(assemble(saved)));
    }

    private Mono<Void> saveSkills(Long employeeId, List<String> skills) {
        if (skills == null || skills.isEmpty()) {
            return Mono.empty();
        }
        return this.skills.saveAll(skills.stream()
                        .map(skill -> new EmployeeSkill(null, employeeId, skill))
                        .toList())
                .then();
    }

    private Mono<Void> saveTechnologies(Long employeeId, List<Long> technologyIds) {
        if (technologyIds == null || technologyIds.isEmpty()) {
            return Mono.empty();
        }
        return employeeTechnologies.saveAll(technologyIds.stream()
                        .map(techId -> new EmployeeTechnology(null, employeeId, techId))
                        .toList())
                .then();
    }

    private Mono<EmployeeResponse> assemble(Employee employee) {
        Mono<List<String>> skillsMono = skills.findByEmployeeId(employee.getId())
                .map(EmployeeSkill::getSkill)
                .collectList();
        Mono<Map<Long, String>> techNameById = technologies.findAll()
                .collect(Collectors.toMap(t -> t.getId(), t -> t.getName()));
        Mono<List<Long>> techIds = employeeTechnologies.findByEmployeeId(employee.getId())
                .map(EmployeeTechnology::getTechnologyId)
                .collectList();

        return Mono.zip(skillsMono, techIds, techNameById)
                .map(tuple -> toResponse(employee, tuple.getT1(), tuple.getT2(), tuple.getT3()));
    }

    private EmployeeResponse toResponse(Employee employee, List<String> employeeSkills,
                                        List<Long> techIds, Map<Long, String> techNameById) {
        List<EmployeeResponse.TechnologyRef> refs = techIds.stream()
                .filter(techNameById::containsKey)
                .map(id -> new EmployeeResponse.TechnologyRef(id, techNameById.get(id)))
                .toList();
        return new EmployeeResponse(employee.getId(), employee.getName(), employee.getEmail(),
                employee.getPosition(), employeeSkills, refs);
    }
}