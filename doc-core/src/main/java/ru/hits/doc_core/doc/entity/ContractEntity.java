package ru.hits.doc_core.doc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.hits.doc_core.client.entity.ClientEntity;
import ru.hits.doc_core.client.entity.EmployeeEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "contract")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContractEntity {
    @Id
    private UUID id;
    private int number;
    private LocalDate date;
    @JoinColumn(name = "client", referencedColumnName = "id")
    @ManyToOne
    private ClientEntity client;
    private boolean volume;
    private double price;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_doing_date")
    private LocalDate endDoingDate;
    @Column(name = "end_life_date")
    private LocalDate endLifeDate;
    private String subject;
    @JoinColumn(name = "employee", referencedColumnName = "id")
    @ManyToOne
    private EmployeeEntity employee;
    @Column(name = "is_end")
    private boolean isEnd;
}
