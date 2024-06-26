package ru.hits.doc_core.client.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BICEntity {
    @Id
    private Integer code;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "address")
    private String address;
    @Column(name = "bill")
    private String bill;
}
