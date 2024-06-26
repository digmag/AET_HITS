package ru.hits.doc_core.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hits.common.dtos.client.*;
import ru.hits.common.security.JwtUserData;
import ru.hits.common.security.exception.ForbiddenException;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.doc_core.client.entity.ClientEntity;
import ru.hits.doc_core.client.entity.OPFEntity;
import ru.hits.doc_core.client.entity.Requisites;
import ru.hits.doc_core.client.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final BICRepository bicRepository;
    private final EmployeeRepository employeeRepository;
    private final OPFRepository opfRepository;
    private final RequisiteRepository requisiteRepository;

    @Transactional
    public ResponseEntity<?> create(CreateDTO clientCreateDTO, Authentication authentication){
        var user = (JwtUserData) authentication.getPrincipal();
        var employee = employeeRepository.findById(user.getId());
        if(employee.isEmpty()){
            throw new NotFoundException("Работник не найден");
        }
        if(!employee.get().isAdmin()){
            throw new ForbiddenException("Работник не является админом");
        }
        Optional<OPFEntity> opf = opfRepository.findById(clientCreateDTO.getOpf());
        if(opf.isEmpty() && (clientCreateDTO.getOpf() != null)){
            throw new NotFoundException("Организационно правовая форма не найдена в справочнике");
        }
        ClientEntity client = new ClientEntity(
                UUID.randomUUID(),
                clientCreateDTO.isClientType()?"PHYSICAL":"LAW",
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
        clientCreateDTO.getBankRequisites().forEach(requisiteDTO -> {
            var bic = bicRepository.findById(requisiteDTO.getBic());
            if(bic.isEmpty()){
                throw new NotFoundException("Не удалось найти бик с кодом "+ requisiteDTO.getRequisite());
            }
            var requisite = new Requisites(
                    UUID.randomUUID(),
                    client,
                    bic.get(),
                    requisiteDTO.getRequisite()
            );
            requisiteRepository.save(requisite);
        });
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
    public ResponseEntity<?> update (UpdateDTO createDTO, UUID id, Authentication authentication) {
        var user = (JwtUserData) authentication.getPrincipal();
        var employee = employeeRepository.findById(user.getId());
        if(employee.isEmpty()){
            throw new NotFoundException("Не удалось найти работника");
        }
        if(!employee.get().isAdmin()){
            throw new ForbiddenException("Работник не является администратором");
        }
        Optional<ClientEntity> client = clientRepository.findById(id);
        if(client.isEmpty()){
            throw new NotFoundException("Клиент не найден");
        }
        Optional<OPFEntity> opf = opfRepository.findById(createDTO.getOpf());
        if(opf.isEmpty() && (createDTO.getOpf() != null)){
            throw new NotFoundException("Организационно правовая форма не найдена в справочнике");
        }
        client.get().setFaceType(createDTO.isClientType()?"PHYSICAL":"LAW");
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
        List<ResponseRequisite> requisiteCreateDTOS = new ArrayList<>();
        createDTO.getBankRequisites().forEach(requisiteDTO -> {
            var req = requisiteRepository.findById(requisiteDTO.getId());
            if(req.isEmpty()){
                throw new NotFoundException("Не удалось найти счет с кодом "+ requisiteDTO.getId());
            }
            var bic = bicRepository.findById(requisiteDTO.getBic());
            if(bic.isEmpty()){
                throw new NotFoundException("Не удалось найти бик с кодом "+ requisiteDTO.getBic());
            }
            req.get().setBill(requisiteDTO.getRequisite());
            req.get().setBic(bic.get());
            requisiteRepository.save(req.get());
            requisiteCreateDTOS.add(new ResponseRequisite(req.get().getId(),req.get().getBic().getBankName(), req.get().getBill()));
        });
        return ResponseEntity.ok(
                new ClientFullResponseDTO(
                        client.get().getId(),
                        client.get().getFaceType(),
                        requisiteCreateDTOS,
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
        requisiteRepository.findAllByClient(clientRepository.findById(id).get()).forEach(requisites -> {
            requisiteRepository.delete(requisites);
        });
        clientRepository.deleteById(id);
        return ResponseEntity.ok("Удален");
    }

    public ResponseEntity<?> list (Integer page, String isLaw, String inn, String name, String email, String ceoName){
        page = page==null? 0: page;
        var pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "shortName"));
        Page<ClientEntity> clientEntityPage = clientRepository.findAll(clientListSpecification(isLaw, inn, name,email,ceoName), pageRequest);
        if(page>clientEntityPage.getTotalPages()){
            throw new NotFoundException("Страница не найдена");
        }
        List<ClientShortResponseDTO> clientShortResponseDTOS = new ArrayList<>();
        clientEntityPage.forEach(clientEntity -> {
            clientShortResponseDTOS.add(new ClientShortResponseDTO(
                    clientEntity.getId(),
                    clientEntity.getFaceType(),
                    clientEntity.getFullName(),
                    clientEntity.getCEOFullName(),
                    clientEntity.getINN(),
                    clientEntity.getPhone(),
                    clientEntity.getEmail()
            ));
        });
        PageDTO pageDTO = new PageDTO(clientShortResponseDTOS, page, clientEntityPage.getTotalPages());
        return ResponseEntity.ok(pageDTO);
    }

    private Specification<ClientEntity> clientListSpecification(String isLaw, String inn, String name, String email, String ceoName){
        var spec = new ArrayList<Specification<ClientEntity>>();
        if(isLaw.isEmpty() && inn.isEmpty() && name.isEmpty() && email.isEmpty() && ceoName.isEmpty())
            return Specification.allOf();
        if(!isLaw.isEmpty()){
            spec.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("faceType"), isLaw));
        }
        if(!inn.isEmpty()){
            spec.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("INN"), inn));
        }
        if(!name.isEmpty()){
            spec.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("shortName"), name));
        }
        if(!email.isEmpty()){
            spec.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("email"),email));
        }
        if(!ceoName.isEmpty()){
            spec.add((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("CEOFullName"),ceoName));
        }
        return Specification.allOf(spec);
    }

    @Transactional
    public ResponseEntity<?> getClient(UUID id){
        Optional<ClientEntity> client = clientRepository.findById(id);
        if(client.isEmpty()){
            throw new NotFoundException("Клиент не найден");
        }
        List<ResponseRequisite> requisiteCreateDTOS = new ArrayList<>();
        requisiteRepository.findAllByClient(client.get()).forEach(requisites -> {
            requisiteCreateDTOS.add(new ResponseRequisite(requisites.getId(),requisites.getBic().getBankName(), requisites.getBill()));
        });
        return ResponseEntity.ok(
                new ClientFullResponseDTO(
                        client.get().getId(),
                        client.get().getFaceType(),
                        requisiteCreateDTOS,
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
    public ResponseEntity<?> getBic(String name){
        if(name.isEmpty()){
            return ResponseEntity.ok(bicRepository.findAll());
        }
        return ResponseEntity.ok(bicRepository.findAllByBankName(name));
    }

    public ResponseEntity<?> getOpf(String name){
        if(name.isEmpty()){
            return ResponseEntity.ok(opfRepository.findAll());
        }
        return ResponseEntity.ok(opfRepository.findAllByName(name));
    }
}
