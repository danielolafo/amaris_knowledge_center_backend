package com.amaris.knowledgecenter.repository;

import com.amaris.knowledgecenter.domain.ImplementationEmployee;
import java.util.List;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ImplementationEmployeeRepository extends ReactiveCrudRepository<ImplementationEmployee, Long> {

    Flux<ImplementationEmployee> findByImplementationIdIn(List<Long> implementationIds);

    Flux<ImplementationEmployee> findByImplementationId(Long implementationId);
}