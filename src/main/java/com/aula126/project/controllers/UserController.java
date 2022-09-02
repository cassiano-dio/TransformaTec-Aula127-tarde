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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aula126.project.models.User;
import com.aula126.project.repositories.UserRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
//localhost:8080/api/...
public class UserController{
    
    @Autowired
    UserRepository userRepository;

    //CRIAÇÃO DE USUÁRIO

    //localhost:8080/api/users - POST
    @PostMapping("/users")
    //Documentação do Swagger
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 201, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ApiOperation(value = "Criando novo usuário", consumes = "application/json", produces = "application/json")

    public ResponseEntity<User> createUser(@RequestBody User user){

        User _user = userRepository.save(user);

        return new ResponseEntity<User>(_user, HttpStatus.OK);
    }

    //BUSCA DE USUÁRIO

    @GetMapping("/users")
    //localhost:8080/api/users/?username=wagner - GET
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ApiOperation(value = "Buscando usuários por nome", produces = "application/json")
    public ResponseEntity<List<User>> findByName(@RequestParam String username){

        List<User> _users = userRepository.findByUsername(username);

        if(_users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<User>>(_users, HttpStatus.OK);

    }


    @GetMapping("users/{id}")
    //localhost:8080/api/users/1 - GET

    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @ApiOperation(value = "Buscando um usuário por id", produces = "application/json")
    public ResponseEntity<User> getById(@PathVariable("id") long id){

        User _user = userRepository.findById(id);

        if (_user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);   
        }

        return new ResponseEntity<User>(_user, HttpStatus.OK);

    }


}
