package com.engeto.genesis.controller;

import com.engeto.genesis.domain.UserInfo;
import com.engeto.genesis.model.UserInfoDTO;
import com.engeto.genesis.service.UserInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserInfoService userInfoService;

    public UserController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/user")
    public ResponseEntity createUser(@RequestBody UserInfo userInfo) {
        UserInfo checkUserInfo = userInfoService.createUser(userInfo);
        if (checkUserInfo != null) {
            return new ResponseEntity<>(checkUserInfo, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUserById(@PathVariable Long id) {
        UserInfo userInfoById = userInfoService.getUserById(id);
        if (userInfoById == null) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("User with id: " + id + " does not exist");
        }
        return new ResponseEntity(userInfoById, HttpStatus.OK);
    }




    @GetMapping("/users")
    public ResponseEntity getAll(@RequestParam(name = "detail", defaultValue = "false") boolean detail) {
        userInfoService.createUser(new UserInfo("mike", "wazovsky", "somePersonId", "someUuid"));   //TODO just for test
        if (detail) {
            List<UserInfoDTO> usersList = userInfoService.findAllUsers();
            return new ResponseEntity<>(usersList, HttpStatus.OK);
        } else {
            List<UserInfoDTO> usersList = userInfoService.findAllUsers();
            return new ResponseEntity<>(usersList, HttpStatus.OK);
        }


    }

//    @GetMapping("/users")   //TODO user detail
//    public ResponseEntity<List<UserInfoDTO>> getAll() {
//        userInfoService.createUser(
//                new UserInfo("mike",
//                        "wazovsky",
//                        "somePersonId",
//                        "someUuid"));   //TODO just for test
//        List<UserInfoDTO> usersList = userInfoService.findAllUsers();
//        return new ResponseEntity<>(usersList, HttpStatus.OK);
//    }


    @PutMapping("/user/{id}")
    public ResponseEntity updateUserById(@PathVariable("id") Long id, @RequestBody UserInfo updateUserInfo) {
        if (userInfoService.getUserById(id) != null) {
            userInfoService.updateUserById(id, updateUserInfo);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("User with id: " + id + " does not exist");
        }

    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        if (userInfoService.getUserById(id) != null) {
            userInfoService.delete(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("User with id: " + id + " does not exist");
        }
    }

}
