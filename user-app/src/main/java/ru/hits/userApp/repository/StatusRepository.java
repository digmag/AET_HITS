package ru.hits.userApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hits.userApp.entity.StatusEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, UUID> {

    @Query(value = "SELECT * FROM work_status WHERE status LIKE %:status%",nativeQuery = true)
    List<StatusEntity> findAllByStatus(String status);
}
