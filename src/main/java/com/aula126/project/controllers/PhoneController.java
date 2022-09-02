package com.aula126.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aula126.project.models.Contact;
import com.aula126.project.models.Phone;
import com.aula126.project.repositories.ContactRepository;
import com.aula126.project.repositories.PhoneRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
public class PhoneController {

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    ContactRepository contactRepository;

    @PostMapping("/contacts/{id}/phones")
    //localhost:8080/api/contacts/1/phones

    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @ApiOperation(value = "Criando um novo telefone para um contato", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Phone> addPhoneNumber(
        @PathVariable("id") long id,
        @RequestBody Phone phone
    ){

        Contact _contact = contactRepository.findById(id);
        
        phone.setContact(_contact);

        Phone _phone = phoneRepository.save(phone);

        return new ResponseEntity<Phone>(_phone, HttpStatus.CREATED);
    }

    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error"),
    })
    @ApiOperation(value = "Buscando lista de telefones de um contato", consumes = "application/json", produces = "application/json")
    @GetMapping("/contacts/{id}/phones")
    //localhost:8080/api/contacts/1/phones
    public ResponseEntity<List<Phone>> getByContact(@PathVariable("id") long id){

        List<Phone> _phones = phoneRepository.findByContact(id);

        if(_phones.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Phone>>(_phones, HttpStatus.OK);

    }
    
}
