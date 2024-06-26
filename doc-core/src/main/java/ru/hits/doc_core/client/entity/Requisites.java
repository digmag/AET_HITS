package ru.hits.doc_core.client.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="requisite")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Requisites {
    @Id
    private UUID id;
    @OneToOne
    @JoinColumn(name = "client", referencedColumnName = "id")
    private ClientEntity client;
    @ManyToOne
    @JoinColumn(name="bic", referencedColumnName = "code")
    private BICEntity bic;
    private String bill;
}
