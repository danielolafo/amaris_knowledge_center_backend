package com.amaris.knowledgecenter.repository;

import com.amaris.knowledgecenter.domain.Technology;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TechnologyRepository extends ReactiveCrudRepository<Technology, Long> {
}