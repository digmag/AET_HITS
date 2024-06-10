package ru.hits.notificationApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.hits.common.security.SecurityConfig;
import ru.hits.common.security.exception.GlobalExceptionHandler;

@SpringBootApplication
@Import({SecurityConfig.class,  GlobalExceptionHandler.class})
public class NotificationServer {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServer.class, args);
    }
}