package ru.hits.doc_core.client.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity {
    @Id
    private UUID id;
    @Column(name="face_type")
    private String faceType;
    @Column(name = "unique_tax_number")
    private String INN;
    @Column(name = "cpp")
    private String CPP;
    @ManyToOne
    @JoinColumn(name = "opf", referencedColumnName = "id")
    private OPFEntity opf;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "ceo_full_name")
    private String CEOFullName;
    @Column(name = "ceo_status")
    private String CEOStatus;
    private String address;
    private String phone;
    private String email;
    private String comment;
}
