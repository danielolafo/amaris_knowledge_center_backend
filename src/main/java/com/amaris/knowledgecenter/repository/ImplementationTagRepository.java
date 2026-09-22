package com.amaris.knowledgecenter.repository;

import com.amaris.knowledgecenter.domain.ImplementationTag;
import java.util.List;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ImplementationTagRepository extends ReactiveCrudRepository<ImplementationTag, Long> {

    Flux<ImplementationTag> findByImplementationIdIn(List<Long> implementationIds);

    Flux<ImplementationTag> findByImplementationId(Long implementationId);
}