package ru.hits.doc_core.doc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "price_list")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceListEntity {
    @Id
    private UUID id;
    private String law;
    private String name;
    private double price;
}
