package ru.hits.doc_core.doc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hits.common.dtos.doc.PriceListCreateDTO;
import ru.hits.common.security.JwtUserData;
import ru.hits.common.security.exception.BadRequestException;
import ru.hits.common.security.exception.ForbiddenException;
import ru.hits.common.security.exception.NotFoundException;
import ru.hits.doc_core.client.entity.EmployeeEntity;
import ru.hits.doc_core.client.repository.EmployeeRepository;
import ru.hits.doc_core.doc.entity.PriceListEntity;
import ru.hits.doc_core.doc.repository.ContractRepository;
import ru.hits.doc_core.doc.repository.PriceListRepository;
import ru.hits.doc_core.doc.repository.PriceContractRepository;

import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class DocumentService {
    private final PriceListRepository priceListRepository;
    private final EmployeeRepository employeeRepository;
    private final ContractRepository contractRepository;
    private final PriceContractRepository priceContractRepository;

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
        return ResponseEntity.ok(priceListRepository.findAllLikeName(name));
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
}
