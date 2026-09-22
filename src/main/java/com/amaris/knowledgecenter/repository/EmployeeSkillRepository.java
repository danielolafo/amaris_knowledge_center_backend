package com.amaris.knowledgecenter.repository;

import com.amaris.knowledgecenter.domain.EmployeeSkill;
import java.util.List;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EmployeeSkillRepository extends ReactiveCrudRepository<EmployeeSkill, Long> {

    Flux<EmployeeSkill> findByEmployeeIdIn(List<Long> employeeIds);

    Flux<EmployeeSkill> findByEmployeeId(Long employeeId);
}