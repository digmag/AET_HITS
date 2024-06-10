package ru.hits.userApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.user.LoginDTO;
import ru.hits.common.dtos.user.RecoverDTO;
import ru.hits.common.dtos.user.RecoverPasswordDTO;
import ru.hits.common.dtos.user.RegistrationDTO;
import ru.hits.userApp.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/account")
public class UserController {

    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationDTO registrationDTO){
        return userService.registration(registrationDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);
    }

    @PostMapping("/recover")
    public ResponseEntity<?> recover(@RequestBody RecoverDTO recoverDTO){
        return userService.recover(recoverDTO);
    }
    @PutMapping("/recover/{id}")
    public ResponseEntity<?> recoverId(@PathVariable UUID id, @RequestBody RecoverPasswordDTO recoverPasswordDTO){
        return userService.recoverId(id, recoverPasswordDTO);
    }

    @PutMapping("/verification/{id}")
    public ResponseEntity<?> verification(@PathVariable UUID id){
        return userService.verification(id);
    }
}
