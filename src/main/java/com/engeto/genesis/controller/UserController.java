package com.engeto.genesis.controller;

import com.engeto.genesis.model.User;
import com.engeto.genesis.service.UserService;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        User userById = userService.getUserById(id);
        if (userById == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(userById, HttpStatus.OK);
    }


    @GetMapping("/users")
    public ResponseEntity getAll() {
        List<User> usersList = userService.getUsers();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }


}
