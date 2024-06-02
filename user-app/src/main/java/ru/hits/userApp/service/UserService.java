package ru.hits.userApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hits.common.dtos.user.*;
import ru.hits.common.security.SecurityConfig;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.common.security.exception.ForbiddenException;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.userApp.entity.EmployeeEntity;
import ru.hits.userApp.entity.StatusEntity;
import ru.hits.userApp.repository.EmployeeRepository;
import ru.hits.userApp.repository.StatusRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final EmployeeRepository employeeRepository;
    private final SecurityConfig securityConfig;
    private final StatusRepository statusRepository;
    private final KafkaSender kafkaSender;
    private final AuthenticationService authenticationService;
    @Transactional
    public ResponseEntity<?> registration(RegistrationDTO registrationDTO){
        if(registrationDTO.getFullName().isEmpty() ||
                registrationDTO.getEmail().isEmpty() ||
                registrationDTO.getPassword().isEmpty()||
                registrationDTO.getStatus().toString().isEmpty()){
            throw new BadRequestException("Все поля обязательны для заполнения");
        }
        Optional<StatusEntity> statusEntity = statusRepository.findById(registrationDTO.getStatus());
        if(statusEntity.isEmpty()){
            throw new NotFoundException("Должность не найдена");
        }
        EmployeeEntity employee = new EmployeeEntity(
                UUID.randomUUID(),
                registrationDTO.getFullName(),
                registrationDTO.getEmail(),
                securityConfig.bCryptPasswordEncoder().encode(registrationDTO.getPassword()),
                statusEntity.get(),
                false,
                false
        );
        if(employeeRepository.findByEmail(registrationDTO.getEmail()).isPresent()){
            throw new BadRequestException("Пользователь с таким email уже существует");
        }
        if (!validateEmail(registrationDTO.getEmail())){
            throw new BadRequestException("Не допустимый формат почты");
        }
        employeeRepository.save(employee);
        kafkaSender.sendConfirmMessage(employee);
        employeeRepository.save(employee);
        return ResponseEntity.ok("Подтвердите свой аккаунт на почте");
    }

    @Transactional
    public ResponseEntity<?> login(LoginDTO loginDTO){
        Optional<EmployeeEntity> employee = employeeRepository.findByEmail(loginDTO.getEmail());
        if(employee.isEmpty()){
            throw new NotFoundException("Пользователь не найден.");
        }
        if(!employee.get().isVerification()){
            throw new ForbiddenException("Пользователь не подтвердил аккаунт.");
        }
        if(!securityConfig.bCryptPasswordEncoder().matches(loginDTO.getPassword(), employee.get().getPassword())){
            throw new BadRequestException("Вы ввели неверный пароль");
        }
        return ResponseEntity.ok(
                new TokenResponseDTO(authenticationService.generateAccess(employee.get()))
        );
    }
    @Transactional
    public ResponseEntity<?> recover(RecoverDTO recoverDTO){
        Optional<EmployeeEntity> employee = employeeRepository.findByEmail(recoverDTO.getEmail());
        if(employee.isEmpty()){
            throw new NotFoundException("Пользователь не найден");
        }
        kafkaSender.sendRecoveryMessage(employee.get());
        return ResponseEntity.ok(null);
    }

    @Transactional
    public ResponseEntity<?> recoverId(UUID id, RecoverPasswordDTO recoverPasswordDTO){
        Optional<EmployeeEntity> employee = employeeRepository.findById(id);
        if(employee.isEmpty()){
            throw new NotFoundException("Пользователь не найден");
        }
        employee.get().setPassword(securityConfig.bCryptPasswordEncoder().encode(recoverPasswordDTO.getPassword()));
        employeeRepository.save(employee.get());
        return ResponseEntity.ok("Пароль изменен");
    }

    @Transactional
    public ResponseEntity<?> verification(UUID id){
        Optional<EmployeeEntity> employee = employeeRepository.findById(id);
        if(employee.isEmpty()){
            throw new NotFoundException("Пользователь не найден.");
        }
        employee.get().setVerification(true);
        employeeRepository.save(employee.get());
        return ResponseEntity.ok("Пользователь подтвержден");
    }
    private boolean validateEmail(String login){
        Pattern pattern = Pattern.compile("[A-Za-z0-9]+\\@[A-Za-z0-9]+\\.[A-Za-z0-9]+");
        return pattern.matcher(login).matches();
    }
}
