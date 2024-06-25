package ru.hits.common.dtos.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceListResponseDTO {
    private UUID id;
    private String name;
    private Double price;
    private Integer count;
    private Double sum;
}
