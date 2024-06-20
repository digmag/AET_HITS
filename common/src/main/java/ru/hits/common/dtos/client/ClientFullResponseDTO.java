package ru.hits.common.dtos.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientFullResponseDTO {
    private UUID id;
    private String faceType;
    private BicDTO bicId;
    private String inn;
    private String cpp;
    private String opf;
    private String fullName;
    private String shortName;
    private String ceoFullName;
    private String ceoStatus;
    private String address;
    private String phone;
    private String email;
    private String comment;
}
