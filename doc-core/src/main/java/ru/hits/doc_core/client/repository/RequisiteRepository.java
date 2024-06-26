package ru.hits.doc_core.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.doc_core.client.entity.ClientEntity;
import ru.hits.doc_core.client.entity.Requisites;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface RequisiteRepository extends JpaRepository<Requisites, UUID> {
    Optional<Requisites> findByClient(ClientEntity client);
    List<Requisites> findAllByClient(ClientEntity client);
}
