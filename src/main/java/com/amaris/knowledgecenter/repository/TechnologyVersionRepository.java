package com.amaris.knowledgecenter.repository;

import com.amaris.knowledgecenter.domain.TechnologyVersion;
import java.util.List;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TechnologyVersionRepository extends ReactiveCrudRepository<TechnologyVersion, Long> {

    Flux<TechnologyVersion> findByTechnologyIdIn(List<Long> technologyIds);

    Flux<TechnologyVersion> findByTechnologyId(Long technologyId);

    Mono<Void> deleteByTechnologyId(Long technologyId);
}