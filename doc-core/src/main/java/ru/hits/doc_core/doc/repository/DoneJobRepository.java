package ru.hits.doc_core.doc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.hits.doc_core.doc.entity.DoneJob;

import java.util.UUID;

@Repository
public interface DoneJobRepository extends JpaRepository<DoneJob, UUID>, JpaSpecificationExecutor<DoneJob> {
}
