package ru.hits.common.dtos.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceListForContract {
    private UUID id;
    private Integer count;
}
