package ru.hits.common.dtos.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequisiteUpdateDTO {
    private UUID id;
    private Integer bic;
    private String requisite;
}
