package ru.hits.userApp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hits.common.dtos.user.RegistrationDTO;

@Service
public class UserService {

    @Transactional
    public ResponseEntity<?> registration(RegistrationDTO registrationDTO){
        return null;
    }
}
