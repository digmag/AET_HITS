package ru.hits.userApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.userApp.entity.EmployeeEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {
    Optional<EmployeeEntity> findByEmail(String email);
}
