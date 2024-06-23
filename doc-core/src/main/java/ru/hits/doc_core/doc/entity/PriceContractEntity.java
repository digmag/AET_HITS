package ru.hits.doc_core.doc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="price_contract")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceContractEntity {
    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "price_list", referencedColumnName = "id")
    private PriceListEntity priceList;

    @ManyToOne
    @JoinColumn(name = "contract", referencedColumnName = "id")
    private ContractEntity contract;
}
