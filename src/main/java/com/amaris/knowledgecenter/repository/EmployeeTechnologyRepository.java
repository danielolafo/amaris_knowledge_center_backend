package com.amaris.knowledgecenter.repository;

import com.amaris.knowledgecenter.domain.EmployeeTechnology;
import java.util.List;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EmployeeTechnologyRepository extends ReactiveCrudRepository<EmployeeTechnology, Long> {

    Flux<EmployeeTechnology> findByEmployeeIdIn(List<Long> employeeIds);

    Flux<EmployeeTechnology> findByEmployeeId(Long employeeId);
}