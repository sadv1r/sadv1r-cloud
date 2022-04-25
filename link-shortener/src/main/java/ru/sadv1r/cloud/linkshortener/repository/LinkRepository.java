package ru.sadv1r.cloud.linkshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sadv1r.cloud.linkshortener.entity.LinkEntity;
import ru.sadv1r.cloud.linkshortener.entity.LinkOnlyWithUrl;

import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<LinkEntity, String> {

    Optional<LinkOnlyWithUrl> findUrlById(String id);

    @Modifying
    @Query("update LinkEntity l set l.hits = l.hits + 1 where l.id = :id")
    void hit(@Param("id") String id);

}
