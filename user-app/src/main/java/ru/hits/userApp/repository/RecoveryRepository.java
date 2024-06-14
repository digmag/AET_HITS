package ru.hits.userApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.userApp.entity.RecoveryMessageEntity;

import java.util.UUID;

@Repository
public interface RecoveryRepository extends JpaRepository<RecoveryMessageEntity, UUID> {
}
