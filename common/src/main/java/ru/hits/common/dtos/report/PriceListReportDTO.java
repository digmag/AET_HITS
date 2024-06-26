package ru.hits.common.dtos.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceListReportDTO {
    private String priceListPosition;
    private Integer count;
    private Double sum;
}
