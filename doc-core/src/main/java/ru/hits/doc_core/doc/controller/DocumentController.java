package ru.hits.doc_core.doc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hits.common.dtos.doc.PriceListCreateDTO;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/document")
public class DocumentController {

    @GetMapping("/price")
    public ResponseEntity<?> getListOfPrice(@RequestParam(name = "name", defaultValue = "") String name){
        //Получение сущностей прайслиста
        return null;
    }

    @PostMapping("/price")
    public ResponseEntity<?> addToPriceList(@RequestBody PriceListCreateDTO priceListElement){
        //Добавление элемента в прайслист
        return null;
    }

    @PutMapping("/price/{id}")
    public ResponseEntity<?> addToPriceList(@RequestBody PriceListCreateDTO priceListElement, @PathVariable UUID id){
        //Изменение элемента в прайслист
        return null;
    }

    @DeleteMapping("/price")
    public ResponseEntity<?> deleteFromPriceList(@PathVariable UUID id){
        //Удаление из прайслиста
        return null;
    }


    @GetMapping("/contract")
    public ResponseEntity<?> listOfContracts(/*Добавить фильтры*/){
        //Получение списка договоров
        return null;
    }

    @GetMapping("/contract/{id}")
    public ResponseEntity<?> concreteContract(@PathVariable UUID id){
        //Получение конкретного контракта
        return null;
    }

    @GetMapping("/contract/client/{id}")
    public ResponseEntity<?> clientContracts(@PathVariable UUID id){
        //Получение договоров клиентов
        return null;
    }

    @PostMapping("/contract/create")
    public ResponseEntity<?> contractCreate(/*Тело запроса*/){
        return null;
    }
}
