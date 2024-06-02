package ru.hits.notificationApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.message.MessageDTO;
import ru.hits.common.dtos.message.PoolMessage;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final JavaMailSender javaMailSender;

    public void sendMessage(MessageDTO messageDTO){
        if(messageDTO.getPool().stream().anyMatch(poolMessage -> poolMessage.equals(PoolMessage.EMAIL))){
            sendEmail(messageDTO);
        }
    }

    private void sendEmail(MessageDTO messageDTO){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(messageDTO.getTo());
        simpleMailMessage.setSubject(messageDTO.getSubject());
        simpleMailMessage.setText(messageDTO.getMessage());
        javaMailSender.send(simpleMailMessage);
    }
}
