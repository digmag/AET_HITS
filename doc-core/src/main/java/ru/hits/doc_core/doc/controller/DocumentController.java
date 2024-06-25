package ru.hits.doc_core.doc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.contract.ContractCreateDTO;
import ru.hits.common.dtos.doc.PriceListCreateDTO;
import ru.hits.doc_core.doc.service.DocumentService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/document")
public class DocumentController {
    private final DocumentService documentService;

    @GetMapping("/price")
    public ResponseEntity<?> getListOfPrice(@RequestParam(name = "name", defaultValue = "") String name){
        //Получение сущностей прайслиста
        return documentService.priceListList(name);
    }

    @PostMapping("/price")
    public ResponseEntity<?> addToPriceList(@RequestBody PriceListCreateDTO priceListElement, Authentication authentication){
        //Добавление элемента в прайслист
        return documentService.createPriceListPosition(priceListElement,authentication);
    }

    @PutMapping("/price/{id}")
    public ResponseEntity<?> addToPriceList(@RequestBody PriceListCreateDTO priceListElement, @PathVariable UUID id, Authentication authentication){
        //Изменение элемента в прайслист
        return documentService.editPriceList(priceListElement, id, authentication);
    }

    @DeleteMapping("/price")
    public ResponseEntity<?> deleteFromPriceList(@PathVariable UUID id, Authentication authentication){
        //Удаление из прайслиста
        return documentService.deletePriceList(id, authentication);
    }


    @GetMapping("/contract")
    public ResponseEntity<?> listOfContracts(/*Добавить фильтры*/){
        //Получение списка договоров
        return documentService.listOfContracts();
    }

    @GetMapping("/contract/{id}")
    public ResponseEntity<?> concreteContract(@PathVariable UUID id){
        //Получение конкретного контракта
        return documentService.concreteContract(id);
    }

    @GetMapping("/contract/client/{id}")
    public ResponseEntity<?> clientContracts(@PathVariable UUID id){
        //Получение договоров клиентов
        return documentService.clientCotracts(id);
    }

    @PostMapping("/contract/create")
    public ResponseEntity<?> contractCreate(@RequestBody ContractCreateDTO createDTO){
        return documentService.createContract(createDTO);
    }
}
