package ru.hits.common.dtos.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractCreateDTO {
    private Integer number;
    private UUID clientId;
    private String bankCode;
    private Boolean volume;
    private Double price;
    private LocalDate startDate;
    private LocalDate endDoingDate;
    private LocalDate endLifeDate;
    private String subject;
    private UUID employeeId;
    private Boolean isEnd;
    private List<PriceListForContract> pricePositions;
}
