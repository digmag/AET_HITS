package ru.hits.doc_core.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.doc_core.client.entity.OPFEntity;

import java.util.List;

@Repository
public interface OPFRepository extends JpaRepository<OPFEntity, Integer> {
    List<OPFEntity> findAllByName(String name);
}
