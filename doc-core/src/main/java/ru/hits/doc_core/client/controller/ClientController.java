package ru.hits.doc_core.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.client.CreateDTO;
import ru.hits.common.dtos.client.UpdateDTO;
import ru.hits.doc_core.client.service.ClientService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
public class ClientController {
    private final ClientService clientService;
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateDTO clientCreateDTO, Authentication authentication){
        return clientService.create(clientCreateDTO, authentication);
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
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "isLaw", defaultValue = "") String isLaw,
            @RequestParam(name = "inn", defaultValue = "") String inn,
            @RequestParam(name = "name", defaultValue = "") String name,
            @RequestParam(name = "email", defaultValue = "") String email,
            @RequestParam(name = "ceoName", defaultValue = "") String ceoName
    ){
        return clientService.list(page,isLaw,inn,name,email,ceoName);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody UpdateDTO createDTO, @PathVariable UUID id, Authentication authentication){
        return clientService.update(createDTO, id, authentication);
    }

    @GetMapping("/bic")
    public ResponseEntity<?> bicList(@RequestParam(name = "name", defaultValue = "")String name){
        return clientService.getBic(name);
    }

    @GetMapping("/opf")
    public ResponseEntity<?> opfList(@RequestParam(name = "name", defaultValue = "") String name){
        return clientService.getOpf(name);
    }
}
