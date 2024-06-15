package ru.hits.doc_core.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.doc_core.client.entity.OPFEntity;

@Repository
public interface OPFRepository extends JpaRepository<OPFEntity, Integer> {
}
