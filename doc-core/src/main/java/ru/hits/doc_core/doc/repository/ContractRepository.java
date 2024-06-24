package ru.hits.doc_core.doc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.doc_core.doc.entity.ContractEntity;

import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, UUID> {
}
