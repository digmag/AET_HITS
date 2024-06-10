package ru.hits.userApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hits.userApp.entity.RecoveryMessageEntity;

import java.util.UUID;

public interface RecoveryRepository extends JpaRepository<RecoveryMessageEntity, UUID> {
}
