package ru.hits.doc_core.doc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hits.common.dtos.client.ClientShortResponseDTO;
import ru.hits.common.dtos.contract.ContractCreateDTO;
import ru.hits.common.dtos.contract.ContractResponseDTO;
import ru.hits.common.dtos.doc.PriceListCreateDTO;
import ru.hits.common.dtos.doc.PriceListResponseDTO;
import ru.hits.common.dtos.user.UserDTO;
import ru.hits.common.security.JwtUserData;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.common.security.exception.ForbiddenException;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.doc_core.client.entity.EmployeeEntity;
import ru.hits.doc_core.client.repository.ClientRepository;
import ru.hits.doc_core.client.repository.EmployeeRepository;
import ru.hits.doc_core.doc.entity.ContractEntity;
import ru.hits.doc_core.doc.entity.PriceContractEntity;
import ru.hits.doc_core.doc.entity.PriceListEntity;
import ru.hits.doc_core.doc.repository.ContractRepository;
import ru.hits.doc_core.doc.repository.PriceListRepository;
import ru.hits.doc_core.doc.repository.PriceContractRepository;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


@Service
@RequiredArgsConstructor
public class DocumentService {
    private final PriceListRepository priceListRepository;
    private final EmployeeRepository employeeRepository;
    private final ContractRepository contractRepository;
    private final PriceContractRepository priceContractRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public ResponseEntity<?> createPriceListPosition(PriceListCreateDTO priceListCreateDTO, Authentication authentication){
        if(priceListCreateDTO.getName().isEmpty()||priceListCreateDTO.getPrice() == null ||priceListCreateDTO.getLaw().isEmpty()){
            throw new BadRequestException("Все поля обязательны для заполнения");
        }
        var user = (JwtUserData) authentication.getPrincipal();
        Optional<EmployeeEntity> employee = employeeRepository.findById(user.getId());
        if(employee.isEmpty()){
            throw new NotFoundException("Пользователь не найден");
        }
        if(!employee.get().isAdmin()){
            throw new ForbiddenException("Пользователь не является администратором");
        }
        PriceListEntity priceListEntity = new PriceListEntity(
                UUID.randomUUID(),
                priceListCreateDTO.getLaw(),
                priceListCreateDTO.getName(),
                priceListCreateDTO.getPrice()
        );
        priceListRepository.save(priceListEntity);
        return ResponseEntity.ok(priceListEntity);
    }

    @Transactional
    public ResponseEntity<?> priceListList(String name){
        if(name.isEmpty()){
            return ResponseEntity.ok(priceListRepository.findAll());
        }
        return ResponseEntity.ok(priceListRepository.findByNameStartsWith(name));
    }

    @Transactional
    public ResponseEntity<?> editPriceList(PriceListCreateDTO priceListCreateDTO, UUID id, Authentication authentication){
        if(priceListCreateDTO.getName().isEmpty()||priceListCreateDTO.getPrice() == null ||priceListCreateDTO.getLaw().isEmpty()){
            throw new BadRequestException("Все поля обязательны для заполнения");
        }
        var user = (JwtUserData) authentication.getPrincipal();
        Optional<EmployeeEntity> employee = employeeRepository.findById(user.getId());
        if(employee.isEmpty()){
            throw new NotFoundException("Пользователь не найден");
        }
        if(!employee.get().isAdmin()){
            throw new ForbiddenException("Пользователь не является администратором");
        }
        Optional<PriceListEntity> priceListEntity = priceListRepository.findById(id);
        if(priceListEntity.isEmpty()){
            throw new NotFoundException("Прайс ценник не найден");
        }
        priceListEntity.get().setPrice(priceListCreateDTO.getPrice());
        priceListEntity.get().setLaw(priceListCreateDTO.getLaw());
        priceListEntity.get().setName(priceListCreateDTO.getName());
        return ResponseEntity.ok(priceListEntity);
    }

    @Transactional
    public ResponseEntity<?> deletePriceList(UUID id, Authentication authentication){
        var user = (JwtUserData) authentication.getPrincipal();
        Optional<EmployeeEntity> employee = employeeRepository.findById(user.getId());
        if(employee.isEmpty()){
            throw new NotFoundException("Пользователь не найден");
        }
        if(!employee.get().isAdmin()){
            throw new ForbiddenException("Пользователь не является администратором");
        }
        Optional<PriceListEntity> priceListEntity = priceListRepository.findById(id);
        if(priceListEntity.isEmpty()){
            throw new NotFoundException("Прайс ценник не найден");
        }
        priceListRepository.deleteById(id);
        return ResponseEntity.ok("Успешно удалено");
    }


    @Transactional
    public ResponseEntity<?> createContract(ContractCreateDTO createDTO){
        if(createDTO.getNumber() == null ||
        createDTO.getBankCode() == null ||
        createDTO.getClientId() == null ||
        createDTO.getSubject() == null ||
        createDTO.getEmployeeId() == null ||
        createDTO.getStartDate() == null ||
        createDTO.getEndDoingDate() == null ||
        createDTO.getEndLifeDate() == null ||
        createDTO.getPricePositions() == null ||
        createDTO.getIsEnd() == null ||
        createDTO.getVolume() == null){
            throw new BadRequestException("Все поля кроме цены обязательны к заполнению");
        }
        var client = clientRepository.findById(createDTO.getClientId());
        if(client.isEmpty()){
            throw new NotFoundException("Клиент не найден");
        }
        var employee = employeeRepository.findById(createDTO.getEmployeeId());
        if(employee.isEmpty()){
            throw new NotFoundException("Работник не найден");
        }
        AtomicReference<Double> customPrice = new AtomicReference<>((createDTO.getPrice() != null?createDTO.getPrice():(double)0));
        List<PriceContractEntity> priceContractEntities = new ArrayList<>();
        createDTO.getPricePositions().forEach(pos -> {
            var priceListPosition = priceListRepository.findById(pos.getId());
            if(createDTO.getVolume()){
                if(pos.getCount() == null || pos.getCount()==0){
                    throw new BadRequestException("Выбраны договоры с объемом");
                }
            }
            if(priceListPosition.isEmpty()){
                throw new NotFoundException("Позиция прайслиста не найдена");
            }
            priceContractEntities.add(new PriceContractEntity(
                    UUID.randomUUID(),
                    priceListPosition.get(),
                    null,
                    pos.getCount()
            ));
            if(createDTO.getPrice() == null){
                customPrice.updateAndGet(v -> (v + priceListPosition.get().getPrice()));
            }
        });

        ContractEntity contract = new ContractEntity(
                UUID.randomUUID(),
                createDTO.getNumber(),
                createDTO.getStartDate(),
                client.get(),
                createDTO.getVolume(),
                customPrice.get(),
                createDTO.getStartDate(),
                createDTO.getEndDoingDate(),
                createDTO.getEndLifeDate(),
                createDTO.getSubject(),
                employee.get(),
                createDTO.getIsEnd()
        );
        contractRepository.save(contract);
        List<PriceListResponseDTO> priceListResponseDTOS = new ArrayList<>();
        priceContractEntities.forEach(priceContract -> {
            priceContract.setContract(contract);
            priceListResponseDTOS.add(new PriceListResponseDTO(
                    priceContract.getPriceList().getId(),
                    priceContract.getPriceList().getName(),
                    priceContract.getPriceList().getPrice(),
                    priceContract.getCount(),
                    priceContract.getPriceList().getPrice()*priceContract.getCount()
            ));
            priceContractRepository.save(priceContract);
        });

        return ResponseEntity.ok(new ContractResponseDTO(
                contract.getId(),
                contract.getNumber(),
                contract.getDate(),
                new ClientShortResponseDTO(
                        client.get().getId(),
                        client.get().getFaceType(),
                        client.get().getFullName(),
                        client.get().getCEOFullName(),
                        client.get().getINN(),
                        client.get().getPhone(),
                        client.get().getEmail()
                ),
                contract.isVolume(),
                contract.getPrice(),
                contract.getStartDate(),
                contract.getEndDoingDate(),
                contract.getEndLifeDate(),
                contract.getSubject(),
                new UserDTO(
                        employee.get().getId(),
                        employee.get().getFullName(),
                        employee.get().getEmail(),
                        employee.get().getStatus().getStatus(),
                        employee.get().isAdmin(),
                        employee.get().isVerification()
                ),
                contract.isEnd(),
                priceListResponseDTOS
        ));
    }
}
