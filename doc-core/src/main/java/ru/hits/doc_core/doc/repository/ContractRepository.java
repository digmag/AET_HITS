package ru.hits.doc_core.doc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.doc_core.client.entity.ClientEntity;
import ru.hits.doc_core.client.entity.EmployeeEntity;
import ru.hits.doc_core.doc.entity.ContractEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, UUID> {
    List<ContractEntity> findAllByClient(ClientEntity client);
    List<ContractEntity> findAllByEmployee(EmployeeEntity employee);
}
