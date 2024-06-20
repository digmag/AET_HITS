package ru.hits.common.dtos.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDTO {
    private boolean clientType;
    private Integer bicId;
    private String inn;
    private String cpp;
    private Integer opf;
    private String fullName;
    private String shortName;
    private String ceoFullName;
    private String ceoStatus;
    private String address;
    private String phone;
    private String email;
    private String comment;
}
