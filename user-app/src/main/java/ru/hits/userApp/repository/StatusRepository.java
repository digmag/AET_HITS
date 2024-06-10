package ru.hits.userApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.userApp.entity.StatusEntity;

import java.util.UUID;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, UUID> {
}
