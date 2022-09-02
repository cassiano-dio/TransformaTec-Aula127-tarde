package com.aula126.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aula126.project.models.Contact;
import com.aula126.project.models.Group;
import com.aula126.project.models.User;
import com.aula126.project.repositories.ContactRepository;
import com.aula126.project.repositories.GroupRepository;
import com.aula126.project.repositories.UserRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api")
public class GroupController {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    UserRepository userRepository;

    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping("/user/{userId}/groups")
    @ApiOperation(value = "Criando novo grupo", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Group> createGroup(@RequestBody Group group, @PathVariable("userId") long userId) {

        User _user = userRepository.findById(userId);

        group.setUser(_user);

        Group _group = groupRepository.save(group);

        return new ResponseEntity<>(_group, HttpStatus.CREATED);

    }

    @GetMapping("/groups/{id}")
    @ApiOperation(value = "Listando contatos de grupo", produces = "application/json")
    public ResponseEntity<List<Contact>> getByGroup(@PathVariable("id") long id){

        List<Contact> _contacts = contactRepository.findByGroup(id);

        if(_contacts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Contact>>(_contacts, HttpStatus.OK);

    }

    
}
