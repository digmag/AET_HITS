package ru.hits.userApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hits.common.dtos.user.RegistrationDTO;
import ru.hits.userApp.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/account")
public class UserController {

    private final UserService userService;

    @GetMapping("/registration")
    public ResponseEntity<?> registration(RegistrationDTO registrationDTO){
        return userService.registration(registrationDTO);
    }
}
