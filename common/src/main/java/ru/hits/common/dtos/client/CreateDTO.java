package ru.hits.common.dtos.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDTO {
    private boolean clientType;
    private Integer bicId;
    private String INN;
    private String CPP;
    private Integer OPF;
    private String fullName;
    private String shortName;
    private String CEOFullName;
    private String CEOStatus;
    private String address;
    private String phone;
    private String email;
    private String comment;
}
