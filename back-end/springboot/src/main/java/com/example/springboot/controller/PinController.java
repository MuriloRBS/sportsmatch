package com.example.springboot.controller;

import com.example.springboot.dto.PinRecordDto;
import com.example.springboot.model.PinModel;
import com.example.springboot.repository.PinRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
public class PinController {

    @Autowired
    PinRepository pinRepository;

    @PostMapping("/pins")
    public ResponseEntity<PinModel> savePin(@RequestBody @Valid PinRecordDto pinRecordDto){
        PinModel pinModel = new PinModel();
        BeanUtils.copyProperties(pinRecordDto, pinModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(pinRepository.save(pinModel));
    }

    @GetMapping("/pins")
    public ResponseEntity<List<PinModel>> getAllPins(){
        return ResponseEntity.status(HttpStatus.OK).body(pinRepository.findAll());
    }

    @GetMapping("/pins/{id}")
    public ResponseEntity<Object> getOnePin(@PathVariable(value = "id")UUID id) {
        Optional<PinModel> pin0 = pinRepository.findById(id);
        if (pin0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pin não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pin0.get());
    }

    @PutMapping("/pins/{id}")
    public ResponseEntity<Object> updatePin(@PathVariable(value = "id") UUID id,
                                            @RequestBody @Valid PinRecordDto pinRecordDto){
        Optional<PinModel> pin0 = pinRepository.findById(id);
        if (pin0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pin não encontrado.");
        }
        var pinModel = pin0.get();
        BeanUtils.copyProperties(pinRecordDto, pinModel);
        return ResponseEntity.status(HttpStatus.OK).body(pinRepository.save(pinModel));
    }

    @DeleteMapping("/pins/{id}")
    public ResponseEntity<Object> deletePin(@PathVariable(value = "id") UUID id){
        Optional<PinModel> pin0 = pinRepository.findById(id);
        if (pin0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pin não encontrado.");
        }
        pinRepository.delete(pin0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Pin deletado com sucesso.");
    }
}
