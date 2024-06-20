package ru.hits.doc_core.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hits.common.dtos.client.ClientShortResponseDTO;
import ru.hits.common.dtos.client.CreateDTO;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.doc_core.client.entity.BICEntity;
import ru.hits.doc_core.client.entity.ClientEntity;
import ru.hits.doc_core.client.entity.OPFEntity;
import ru.hits.doc_core.client.repository.BICRepository;
import ru.hits.doc_core.client.repository.ClientRepository;
import ru.hits.doc_core.client.repository.EmployeeRepository;
import ru.hits.doc_core.client.repository.OPFRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final BICRepository bicRepository;
    private final EmployeeRepository employeeRepository;
    private final OPFRepository opfRepository;

    @Transactional
    public ResponseEntity<?> create(CreateDTO clientCreateDTO){
        Optional<BICEntity> bic = bicRepository.findById(clientCreateDTO.getBicId());
        if(bic.isEmpty()){
            throw new NotFoundException("Не удалось найти код БИК");
        }
        Optional<OPFEntity> opf = opfRepository.findById(clientCreateDTO.getOpf());
        if(opf.isEmpty() && (clientCreateDTO.getOpf() != null)){
            throw new NotFoundException("Организационно правовая форма не найдена в справочнике");
        }
        ClientEntity client = new ClientEntity(
                UUID.randomUUID(),
                clientCreateDTO.isClientType()?"PHYSICAL":"LAW",
                bic.get(),
                clientCreateDTO.getInn(),
                clientCreateDTO.getCpp(),
                opf.orElse(null),
                clientCreateDTO.getFullName(),
                !clientCreateDTO.getShortName().isEmpty()?clientCreateDTO.getShortName():"",
                clientCreateDTO.getCeoFullName(),
                clientCreateDTO.getCeoStatus(),
                clientCreateDTO.getAddress(),
                clientCreateDTO.getPhone(),
                clientCreateDTO.getEmail(),
                clientCreateDTO.getComment()
        );
        clientRepository.save(client);
        return ResponseEntity.ok(new ClientShortResponseDTO(
                client.getId(),
                client.getFaceType(),
                client.getFullName(),
                client.getCEOFullName(),
                client.getINN(),
                client.getPhone(),
                client.getEmail()
        ));
    }
}
