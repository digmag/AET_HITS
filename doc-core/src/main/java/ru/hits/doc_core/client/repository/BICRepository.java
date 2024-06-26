package ru.hits.doc_core.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.doc_core.client.entity.BICEntity;

import java.util.List;

@Repository
public interface BICRepository extends JpaRepository<BICEntity, Integer> {
    List<BICEntity> findAllByBankName(String bankName);
}
