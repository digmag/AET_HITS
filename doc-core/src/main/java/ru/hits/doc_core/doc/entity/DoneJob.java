package ru.hits.doc_core.doc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.hits.doc_core.client.entity.EmployeeEntity;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="done_job")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoneJob {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "service", referencedColumnName = "id")
    private PriceContractEntity service;
    @ManyToOne
    @JoinColumn(name = "employee", referencedColumnName = "id")
    private EmployeeEntity employee;

    private LocalDate date;
}
