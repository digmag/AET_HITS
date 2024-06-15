package ru.hits.doc_core.client.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "opf")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OPFEntity {
    @Id
    private Integer id;
    private String name;
}
