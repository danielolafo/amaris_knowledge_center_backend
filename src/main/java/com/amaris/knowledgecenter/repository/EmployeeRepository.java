package com.amaris.knowledgecenter.repository;

import com.amaris.knowledgecenter.domain.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Long> {
}