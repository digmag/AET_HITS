package ru.hits.doc_core.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hits.doc_core.client.repository.ClientRepository;

@RequiredArgsConstructor
@Service
public class ClientService {
    private ClientRepository clientRepository;

    public ResponseEntity<?> create(){
        return null;
    }
}
