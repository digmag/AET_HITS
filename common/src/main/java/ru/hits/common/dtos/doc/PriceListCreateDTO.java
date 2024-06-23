package ru.hits.common.dtos.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PriceListCreateDTO {
    private String law;
    private String name;
    private double price;
}
