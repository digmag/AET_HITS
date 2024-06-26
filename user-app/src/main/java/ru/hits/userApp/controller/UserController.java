package ru.hits.userApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.admin.WorkStatusCreateDTO;
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

    @GetMapping("/status")
    public ResponseEntity<?> StatusByName(@RequestParam(name = "name", defaultValue = "") String name){
        return userService.getStatus(name);
    }

    @PostMapping("/add/status")
    public ResponseEntity<?> addWorkStatus(@RequestBody WorkStatusCreateDTO workStatusCreateDTO, Authentication authentication){
        //Добавление должности
        return userService.addWorkStatus(authentication, workStatusCreateDTO);
    }

    @PutMapping("/add/status/{id}")
    public ResponseEntity<?> editWorkStatus(@RequestBody WorkStatusCreateDTO workStatusCreateDTO, @PathVariable UUID id, Authentication authentication){
        //Изменение должности
        return userService.editStatus(authentication,workStatusCreateDTO,id);
    }

    @GetMapping("/workers")
    public ResponseEntity<?> listWorkers(){
        //Получение списка работников
        return userService.listWorkers();
    }

    @GetMapping("/isAdmin")
    public ResponseEntity<?> isAdmin(Authentication authentication){
        return userService.isAdmin(authentication);
    }
}
