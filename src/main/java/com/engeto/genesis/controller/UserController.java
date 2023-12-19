package com.engeto.genesis.controller;

import com.engeto.genesis.model.User;
import com.engeto.genesis.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getAll(){
        List<User> usersList = userService.getUsers();

        return  new ResponseEntity<>(usersList, HttpStatus.OK);
    }
}
