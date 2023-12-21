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
    public ResponseEntity createUser(@RequestBody User user) {
        User checkUser = userService.createUser(user);
        if (checkUser != null) {
            return new ResponseEntity<>(checkUser, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        User userById = userService.getUserById(id);
        if (userById == null) {
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body("User with id: " + id + " does not exist");
        }
        return new ResponseEntity(userById, HttpStatus.OK);
    }




    @GetMapping("/users")
    public ResponseEntity getAll(@RequestParam(name = "detail", defaultValue = "false") boolean detail) {
        if (detail) {
            List<User> usersList = userService.getUsersDetail();
            return new ResponseEntity<>(usersList, HttpStatus.OK);
        } else {
            List<User> usersList = userService.getUsers();
            return new ResponseEntity<>(usersList, HttpStatus.OK);
        }


    }

//    @GetMapping("/users")
//    public ResponseEntity getAll() {
//
//                   List<User> usersList = userService.getUsers();
//            return new ResponseEntity<>(usersList, HttpStatus.OK);
//        }




    @PutMapping("/user/{id}")
    public ResponseEntity updateUserById(@PathVariable("id") Long id, @RequestBody User updateUser) {
        if (userService.getUserById(id) != null) {
            userService.updateUserById(id, updateUser);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body("User with id: " + id + " does not exist");
        }

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        if (userService.getUserById(id) != null) {
            userService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body("User with id: " + id + " does not exist");
        }
    }

}
