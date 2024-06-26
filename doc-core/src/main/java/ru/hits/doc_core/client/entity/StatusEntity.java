package ru.hits.doc_core.client.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="work_status")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusEntity {
    @Id
    private UUID id;
    private String status;
}
