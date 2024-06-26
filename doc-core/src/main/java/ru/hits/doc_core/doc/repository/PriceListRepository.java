package ru.hits.doc_core.doc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.doc_core.doc.entity.PriceListEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface PriceListRepository extends JpaRepository<PriceListEntity, UUID> {
    List<PriceListEntity> findByNameStartsWith(String name);
}
