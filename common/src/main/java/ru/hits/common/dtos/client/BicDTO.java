package ru.hits.common.dtos.client;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BicDTO {
    private Integer code;
    private String bankName;
    private String address;
    private String bill;
}
