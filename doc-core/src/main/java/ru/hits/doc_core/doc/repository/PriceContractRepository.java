package ru.hits.doc_core.doc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.doc_core.doc.entity.ContractEntity;
import ru.hits.doc_core.doc.entity.PriceContractEntity;
import ru.hits.doc_core.doc.entity.PriceListEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriceContractRepository extends JpaRepository<PriceContractEntity, UUID> {
    List<PriceContractEntity> findAllByContract(ContractEntity contract);
    Optional<PriceContractEntity> findByContractAndPriceList(ContractEntity contract, PriceListEntity priceList);
}
