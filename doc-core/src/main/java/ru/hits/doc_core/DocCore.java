package ru.hits.doc_core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.hits.common.security.SecurityConfig;
import ru.hits.common.security.exception.GlobalExceptionHandler;

@SpringBootApplication
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
public class DocCore {
    public static void main(String[] args) {
        SpringApplication.run(DocCore.class, args);
    }
}