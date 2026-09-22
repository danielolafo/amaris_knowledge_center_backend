package com.amaris.knowledgecenter.repository;

import com.amaris.knowledgecenter.domain.Implementation;
import java.util.List;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ImplementationRepository extends ReactiveCrudRepository<Implementation, Long> {

    @Query("""
            SELECT i.* FROM implementations i
            WHERE LOWER(i.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(COALESCE(i.description, '')) LIKE LOWER(CONCAT('%', :keyword, '%'))
            ORDER BY i.id DESC
            """)
    Flux<Implementation> findByKeyword(String keyword);

    @Query("""
            SELECT i.* FROM implementations i
            INNER JOIN implementation_tags t ON t.implementation_id = i.id
            WHERE LOWER(t.tag) = LOWER(:tag)
            ORDER BY i.id DESC
            """)
    Flux<Implementation> findByTag(String tag);

    Flux<Implementation> findAllByOrderByIdDesc();

    Flux<Implementation> findByIdIn(List<Long> ids);
}