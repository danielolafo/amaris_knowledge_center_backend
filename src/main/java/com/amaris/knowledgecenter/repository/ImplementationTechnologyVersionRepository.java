package com.amaris.knowledgecenter.repository;

import com.amaris.knowledgecenter.domain.ImplementationTechnologyVersion;
import java.util.List;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ImplementationTechnologyVersionRepository
        extends ReactiveCrudRepository<ImplementationTechnologyVersion, Long> {

    Flux<ImplementationTechnologyVersion> findByImplementationIdIn(List<Long> implementationIds);

    Flux<ImplementationTechnologyVersion> findByImplementationId(Long implementationId);
}