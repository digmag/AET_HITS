package ru.hits.doc_core.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hits.common.dtos.client.CreateDTO;
import ru.hits.doc_core.client.service.ClientService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
public class ClientController {
    private ClientService clientService;
    @PostMapping("/create")
    public ResponseEntity<?> create(Authentication authentication, @RequestBody CreateDTO clientCreateDTO){
        return clientService.create(authentication,clientCreateDTO);
    }
}
