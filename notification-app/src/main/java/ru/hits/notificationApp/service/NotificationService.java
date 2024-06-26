package ru.hits.notificationApp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.hits.common.dtos.message.MessageDTO;
import ru.hits.common.dtos.message.PoolMessage;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final JavaMailSender javaMailSender;

    @SneakyThrows
    public void sendMessage(MessageDTO messageDTO){
        if(messageDTO.getPool().stream().anyMatch(poolMessage -> poolMessage.equals(PoolMessage.EMAIL))){
            sendEmail(messageDTO);
        }
    }

    private void sendEmail(MessageDTO messageDTO) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(messageDTO.getTo());
        helper.setSubject(messageDTO.getSubject());
        helper.setText(messageDTO.getMessage(),true);
        javaMailSender.send(message);
    }
}
