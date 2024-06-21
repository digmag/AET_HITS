package ru.hits.doc_core.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.client.CreateDTO;
import ru.hits.doc_core.client.service.ClientService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
public class ClientController {
    private final ClientService clientService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateDTO clientCreateDTO){
        return clientService.create(clientCreateDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(Authentication authentication, @PathVariable UUID id){
        return clientService.delete(authentication, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable UUID id){
        return  clientService.getClient(id);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(
            @RequestParam(name = "page", defaultValue = "5") Integer page
    ){
        return clientService.list(page);
    }
}
