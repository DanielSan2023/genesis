package com.engeto.genesis.controller;

import com.engeto.genesis.model.UserInfoDTO;
import com.engeto.genesis.service.UserInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
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
    public ResponseEntity<UserInfoDTO> createUser(@RequestBody UserInfoDTO userInfoDTO) {
        UserInfoDTO createdUserInfo = userInfoService.createUser(userInfoDTO);
        if (createdUserInfo != null) {
            return new ResponseEntity<>(userInfoDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/user/{id}") //TODO make user detail
    public ResponseEntity<UserInfoDTO> getUserById(@PathVariable Long id,@RequestParam(name = "detail", defaultValue = "false") boolean detail) {
        UserInfoDTO userInfoById;
        if (detail) {
            userInfoById = userInfoService.getUserByIdDetail(id);
        } else {
            userInfoById = userInfoService.getUserById(id);
        }
        if (userInfoById == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(userInfoById, HttpStatus.OK);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserInfoDTO>> getAll(@RequestParam(name = "detail", defaultValue = "false") boolean detail) {
        List<UserInfoDTO> usersList;
        if (detail) {
            usersList = userInfoService.findAllUsersDetail();
        } else {
            usersList = userInfoService.findAllUsers();
        }
        if (CollectionUtils.isEmpty(usersList)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(usersList, HttpStatus.OK);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<HttpStatus> updateUserById(@PathVariable("id") Long id, @RequestBody UserInfoDTO updateUserInfoDTO) {
        if (userInfoService.getUserById(id) != null) {
            userInfoService.updateUserById(id, updateUserInfoDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userInfoService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
