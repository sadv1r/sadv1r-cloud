package ru.sadv1r.cloud.linkshortener.repository;

import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.sadv1r.cloud.linkshortener.entity.LinkEntity;
import ru.sadv1r.cloud.linkshortener.entity.LinkOnlyWithUrl;

@Repository
public interface LinkRepository extends R2dbcRepository<LinkEntity, String> {

    Mono<LinkOnlyWithUrl> findUrlById(String id);

    @Modifying
    @Query("update LinkEntity l set l.hits = l.hits + 1 where l.id = :id")
    void hit(@Param("id") String id);

}
