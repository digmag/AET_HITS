package ru.hits.userApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hits.common.dtos.admin.WorkStatusCreateDTO;
import ru.hits.common.dtos.user.*;
import ru.hits.common.security.JwtUserData;
import ru.hits.common.security.SecurityConfig;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.common.security.exception.ForbiddenException;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.userApp.entity.EmployeeEntity;
import ru.hits.userApp.entity.RecoveryMessageEntity;
import ru.hits.userApp.entity.StatusEntity;
import ru.hits.userApp.repository.EmployeeRepository;
import ru.hits.userApp.repository.RecoveryRepository;
import ru.hits.userApp.repository.StatusRepository;

import java.util.ArrayList;
import java.util.List;
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
    private final RecoveryRepository recoveryRepository;
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
            throw new BadRequestException("Не верный логин.");
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
        RecoveryMessageEntity recoveryMessageEntity = new RecoveryMessageEntity(
                UUID.randomUUID(),
                employee.get(),
                false
        );
        kafkaSender.sendRecoveryMessage(employee.get(), recoveryMessageEntity.getId());
        recoveryRepository.save(recoveryMessageEntity);
        return ResponseEntity.ok(null);
    }

    @Transactional
    public ResponseEntity<?> recoverId(UUID id, RecoverPasswordDTO recoverPasswordDTO){
        Optional<RecoveryMessageEntity> recoveryMessageEntity = recoveryRepository.findById(id);
        if(recoveryMessageEntity.isEmpty()){
            throw new NotFoundException("Запрос на смену пароля не найден");
        }
        if(recoveryMessageEntity.get().isEnd()){
            throw new ForbiddenException("Нельзя заменить пароль по этому запросу");
        }
        recoveryMessageEntity.get().getEmployee().setPassword(securityConfig.bCryptPasswordEncoder().encode(recoverPasswordDTO.getPassword()));
        recoveryMessageEntity.get().setEnd(true);
        employeeRepository.save(recoveryMessageEntity.get().getEmployee());
        recoveryRepository.save(recoveryMessageEntity.get());
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

    @Transactional
    public ResponseEntity<?> getStatus(String name){
        if(name.isEmpty()){
            return ResponseEntity.ok(statusRepository.findAll());
        }
        return ResponseEntity.ok(statusRepository.findAllByStatus(name));
    }

    public ResponseEntity<?> isAdmin(Authentication authentication){
        var user = (JwtUserData) authentication.getPrincipal();
        Optional<EmployeeEntity> employee = employeeRepository.findById(user.getId());
        if(employee.isEmpty()){
            throw new NotFoundException("Пользователь не найден");
        }
        if(!employee.get().isVerification()){
            throw new ForbiddenException("Пользователь не верефицировал свой аккаунт");
        }
        return ResponseEntity.ok(employee.get().isAdmin());
    }

    @Transactional
    public ResponseEntity<?> addWorkStatus(Authentication authentication, WorkStatusCreateDTO workStatusCreateDTO){
        var user = (JwtUserData) authentication.getPrincipal();
        Optional<EmployeeEntity> employee = employeeRepository.findById(user.getId());
        if(employee.isEmpty()){
            throw new NotFoundException("Пользователь не найден");
        }
        if(!employee.get().isVerification()){
            throw new ForbiddenException("Пользователь не верефицировал свой аккаунт");
        }
        if(!employee.get().isAdmin()){
            throw new ForbiddenException("Пользователь не является админом");
        }
        if(workStatusCreateDTO.getName().isEmpty()){
            throw new BadRequestException("Поле должно быть заполнено");
        }
        StatusEntity status = new StatusEntity(
                UUID.randomUUID(),
                workStatusCreateDTO.getName()
        );
        statusRepository.save(status);
        return ResponseEntity.ok(status);
    }

    public ResponseEntity<?> editStatus(Authentication authentication, WorkStatusCreateDTO workStatusCreateDTO, UUID id){
        var user = (JwtUserData) authentication.getPrincipal();
        Optional<EmployeeEntity> employee = employeeRepository.findById(user.getId());
        if(employee.isEmpty()){
            throw new NotFoundException("Пользователь не найден");
        }
        if(!employee.get().isVerification()){
            throw new ForbiddenException("Пользователь не верефицировал свой аккаунт");
        }
        if(!employee.get().isAdmin()){
            throw new ForbiddenException("Пользователь не является админом");
        }
        if(workStatusCreateDTO.getName().isEmpty()){
            throw new BadRequestException("Поле должно быть заполнено");
        }
        if(!statusRepository.existsById(id)){
            throw new NotFoundException("Должность не найдена");
        }
        Optional<StatusEntity> status = statusRepository.findById(id);
        status.get().setStatus(workStatusCreateDTO.getName());
        statusRepository.save(status.get());
        return ResponseEntity.ok(status.get());
    }

    public ResponseEntity<?> listWorkers(){
        List<UserDTO> userDTOS = new ArrayList<>();
        employeeRepository.findAll().forEach(employee -> {
            userDTOS.add(new UserDTO(
                    employee.getId(),
                    employee.getFullName(),
                    employee.getEmail(),
                    employee.getStatus().getStatus(),
                    employee.isAdmin(),
                    employee.isVerification()
            ));
        });
        return ResponseEntity.ok(userDTOS);
    }

    private boolean validateEmail(String login){
        Pattern pattern = Pattern.compile("[A-Za-z0-9]+\\@[A-Za-z0-9]+\\.[A-Za-z0-9]+");
        return pattern.matcher(login).matches();
    }
}
