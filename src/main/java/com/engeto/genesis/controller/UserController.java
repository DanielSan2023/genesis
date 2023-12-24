package com.engeto.genesis.controller;

import com.engeto.genesis.domain.UserInfo;
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
    public ResponseEntity createUser(@RequestBody UserInfo userInfo) {
        UserInfo checkUserInfo = userService.createUser(userInfo);
        if (checkUserInfo != null) {
            return new ResponseEntity<>(checkUserInfo, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        UserInfo userInfoById = userService.getUserById(id);
        if (userInfoById == null) {
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body("User with id: " + id + " does not exist");
        }
        return new ResponseEntity(userInfoById, HttpStatus.OK);
    }




    @GetMapping("/users")
    public ResponseEntity getAll(@RequestParam(name = "detail", defaultValue = "false") boolean detail) {
        userService.createUser(new UserInfo("mike", "wazovsky", "somePersonId", "someUuid"));   //TODO just for test
        if (detail) {
            List<UserInfo> usersList = userService.getUsersDetail();
            return new ResponseEntity<>(usersList, HttpStatus.OK);
        } else {
            List<UserInfo> usersList = userService.getUsers();
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
    public ResponseEntity updateUserById(@PathVariable("id") Long id, @RequestBody UserInfo updateUserInfo) {
        if (userService.getUserById(id) != null) {
            userService.updateUserById(id, updateUserInfo);
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
