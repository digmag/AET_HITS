package ru.hits.userApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.hits.userApp.entity.EmployeeEntity;
import ru.hits.userApp.entity.StatusEntity;
import ru.hits.common.security.SecurityConfig;

@SpringBootApplication
@Import({SecurityConfig.class, EmployeeEntity.class, StatusEntity.class})
public class UserServer {
    public static void main(String[] args){
        SpringApplication.run(UserServer.class, args);
    }
}
