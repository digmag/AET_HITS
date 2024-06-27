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

    @DeleteMapping("/price/{id}")
    public ResponseEntity<?> deleteFromPriceList(@PathVariable UUID id, Authentication authentication){
        //Удаление из прайслиста
        return documentService.deletePriceList(id, authentication);
    }


    @GetMapping("/contract")
    public ResponseEntity<?> listOfContracts(
            Authentication authentication,
            @RequestParam(name = "startDate", defaultValue = "") String startDate,
            @RequestParam(name = "endDate", defaultValue = "") String endDate,
            @RequestParam(name = "client", defaultValue = "") UUID id){
        //Получение списка договоров
        return documentService.listOfContracts(authentication,startDate,endDate,id);
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
    public ResponseEntity<?> contractCreate(@RequestBody ContractCreateDTO createDTO, Authentication authentication){
        return documentService.createContract(createDTO, authentication);
    }

    @PutMapping("/contract/update/{id}")
    public ResponseEntity<?> contractUpdate(@PathVariable UUID id, @RequestBody ContractCreateDTO createDTO, Authentication authentication){
        return documentService.updateContract(createDTO, id, authentication);
    }

    @DeleteMapping("/contract/delete/{id}")
    public ResponseEntity<?> contractDelete(@PathVariable UUID id, Authentication authentication){
        return documentService.deleteContract(id, authentication);
    }

    @PutMapping("/contract/done/{id}")
    public ResponseEntity<?> makeDone(@PathVariable UUID id, Authentication authentication){
        return documentService.makeDone(id, authentication);
    }

    @GetMapping("/report")
    public ResponseEntity<?> report(Authentication authentication,
                                    @RequestParam(name="start", defaultValue = "")String start,
                                    @RequestParam(name="end", defaultValue = "") String end){
        return documentService.report(authentication, start, end);
    }
}
