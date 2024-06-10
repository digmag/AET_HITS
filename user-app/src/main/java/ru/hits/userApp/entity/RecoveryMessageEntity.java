package ru.hits.userApp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "recovery_message")
@AllArgsConstructor
@NoArgsConstructor
public class RecoveryMessageEntity {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private EmployeeEntity employee;
    @Column(name="is_end")
    private boolean isEnd;
}
