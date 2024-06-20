package ru.hits.doc_core.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hits.common.dtos.client.BicDTO;
import ru.hits.common.dtos.client.ClientFullResponseDTO;
import ru.hits.common.dtos.client.ClientShortResponseDTO;
import ru.hits.common.dtos.client.CreateDTO;
import ru.hits.common.security.JwtUserData;
import ru.hits.common.security.exception.ForbiddenException;
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

    @Transactional
    public ResponseEntity<?> update (CreateDTO createDTO, UUID id) {
        Optional<ClientEntity> client = clientRepository.findById(id);
        if(client.isEmpty()){
            throw new NotFoundException("Клиент не найден");
        }
        Optional<BICEntity> bic = bicRepository.findById(createDTO.getBicId());
        if(bic.isEmpty()){
            throw new NotFoundException("Не удалось найти код БИК");
        }
        Optional<OPFEntity> opf = opfRepository.findById(createDTO.getOpf());
        if(opf.isEmpty() && (createDTO.getOpf() != null)){
            throw new NotFoundException("Организационно правовая форма не найдена в справочнике");
        }
        client.get().setFaceType(createDTO.isClientType()?"PHYSICAL":"LAW");
        client.get().setBic(bic.get());
        client.get().setINN(createDTO.getInn());
        client.get().setCPP(createDTO.getCpp());
        client.get().setOpf(opf.orElse(null));
        client.get().setFullName(createDTO.getFullName());
        client.get().setShortName(!createDTO.getShortName().isEmpty()?createDTO.getShortName():"");
        client.get().setCEOFullName(createDTO.getCeoFullName());
        client.get().setCEOStatus(createDTO.getCeoStatus());
        client.get().setAddress(createDTO.getAddress());
        client.get().setPhone(createDTO.getPhone());
        client.get().setEmail(createDTO.getEmail());
        client.get().setComment(createDTO.getComment());
        clientRepository.save(client.get());
        return ResponseEntity.ok(
                new ClientFullResponseDTO(
                        client.get().getId(),
                        client.get().getFaceType(),
                        new BicDTO(client.get().getBic().getCode(), client.get().getBic().getBankName(),client.get().getBic().getAddress(), client.get().getBic().getBill()),
                        client.get().getINN(),
                        client.get().getCPP(),
                        client.get().getOpf().getName(),
                        client.get().getFullName(),
                        client.get().getShortName(),
                        client.get().getCEOFullName(),
                        client.get().getCEOStatus(),
                        client.get().getAddress(),
                        client.get().getPhone(),
                        client.get().getEmail(),
                        client.get().getComment()
                )
        );
    }
    @Transactional
    public ResponseEntity<?> delete (Authentication authentication,UUID id){
        var user = (JwtUserData) authentication.getPrincipal();
        if(employeeRepository.findById(user.getId()).isEmpty()){
            throw new NotFoundException("Работник не найден.");
        }
        if(!employeeRepository.findById(user.getId()).get().isAdmin()){
            throw new ForbiddenException("Пользователь не является админом.");
        }
        if(!clientRepository.existsById(id)){
            throw new NotFoundException("Клиент не найден");
        }
        clientRepository.deleteById(id);
        return ResponseEntity.ok("Удален");
    }

    public ResponseEntity<?> list (Integer page){
        return null;
    }

    @Transactional
    public ResponseEntity<?> getClient(UUID id){
        Optional<ClientEntity> client = clientRepository.findById(id);
        if(client.isEmpty()){
            throw new NotFoundException("Клиент не найден");
        }
        return ResponseEntity.ok(
                new ClientFullResponseDTO(
                        client.get().getId(),
                        client.get().getFaceType(),
                        new BicDTO(client.get().getBic().getCode(), client.get().getBic().getBankName(),client.get().getBic().getAddress(), client.get().getBic().getBill()),
                        client.get().getINN(),
                        client.get().getCPP(),
                        client.get().getOpf().getName(),
                        client.get().getFullName(),
                        client.get().getShortName(),
                        client.get().getCEOFullName(),
                        client.get().getCEOStatus(),
                        client.get().getAddress(),
                        client.get().getPhone(),
                        client.get().getEmail(),
                        client.get().getComment()
                )
        );
    }
}
