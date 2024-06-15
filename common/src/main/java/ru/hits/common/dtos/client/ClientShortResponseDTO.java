package ru.hits.common.dtos.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientShortResponseDTO {
    private UUID id;
    private String faceType;
    private String fullName;
    private String ceoFullName;
    private String inn;
    private String phone;
    private String email;
}
