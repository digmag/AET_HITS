package ru.hits.doc_core.doc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.doc_core.doc.entity.PriceContractEntity;

import java.util.UUID;

@Repository
public interface PriceContractRepository extends JpaRepository<PriceContractEntity, UUID> {
}
