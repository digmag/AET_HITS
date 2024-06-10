package ru.hits.userApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.message.MessageDTO;
import ru.hits.common.dtos.message.PoolMessage;
import ru.hits.userApp.entity.EmployeeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaSender {
    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;

    public void sendConfirmMessage(EmployeeEntity employee){
        List<String> pool = new ArrayList<>(){};
        pool.add(PoolMessage.EMAIL);
        kafkaTemplate.send("registration",new MessageDTO(
                employee.getEmail(),
                "Подтвердите свою учетную запись",
                "<html>" +
                        "<body style='display: flex; flex-direction: row; justify-content: center;'>"+
                        "<h1>Для подтверждения своего аккаунта перейдите по ссылке ниже</h1>"+
                        "<a>http://localhost:5000/verification?id="+employee.getId()+"</a>"+
                        "</body>"+
                        "</html>",
                pool
        ));
    }
    public void sendRecoveryMessage(EmployeeEntity employee, UUID id){
        List<String> pool = new ArrayList<>(){};
        pool.add(PoolMessage.EMAIL);
        kafkaTemplate.send("recovery", new MessageDTO(
                employee.getEmail(),
                "Письмо для восстановления пароля",
                "Для восстановления пароля перейдите по ссылке: http://localhost:5000/recovery/"+id,
                pool
        ));
    }
}
