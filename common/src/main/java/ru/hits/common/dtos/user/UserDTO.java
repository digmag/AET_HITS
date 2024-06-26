package ru.hits.common.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private UUID id;
    private String fullName;
    private String email;
    private String status;
    private boolean admin;
    private boolean verification;
}
