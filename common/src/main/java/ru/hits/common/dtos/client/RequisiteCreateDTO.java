package ru.hits.common.dtos.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequisiteCreateDTO {
    private Integer bic;
    private String requisite;
}
