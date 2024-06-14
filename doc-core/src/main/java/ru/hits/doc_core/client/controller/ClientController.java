package ru.hits.doc_core.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hits.doc_core.client.service.ClientService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
public class ClientController {
    private ClientService clientService;
    @PostMapping("/create")
    public ResponseEntity<?> create(){
        return clientService.create();
    }
}
