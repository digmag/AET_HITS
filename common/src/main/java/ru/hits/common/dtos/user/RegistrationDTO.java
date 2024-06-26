package ru.hits.common.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
    private String fullName;
    private String email;
    private String password;
    private UUID status;
}
