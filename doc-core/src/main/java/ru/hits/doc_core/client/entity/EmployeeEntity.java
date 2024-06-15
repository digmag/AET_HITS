package ru.hits.doc_core.client.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity {
    @Id
    private UUID id;
    @Column(name = "fullname")
    private String fullName;
    private String email;
    private String password;
    @OneToOne
    @JoinColumn(name = "status", referencedColumnName = "id")
    private StatusEntity status;
    private boolean admin;
    private boolean verification;
}
