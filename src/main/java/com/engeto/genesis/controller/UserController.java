package com.engeto.genesis.controller;

import com.engeto.genesis.domain.UserInfo;
import com.engeto.genesis.model.UserInfoDTO;
import com.engeto.genesis.service.UserInfoService;
import org.springframework.beans.BeanUtils;
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
    public ResponseEntity<UserInfoDTO> createUser(@RequestBody UserInfoDTO userInfoDTO) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDTO, userInfo);
        UserInfo createdUserInfo = userInfoService.createUser(userInfo);

        if (createdUserInfo != null) {
            BeanUtils.copyProperties(userInfo, userInfoDTO);
            return new ResponseEntity<>(userInfoDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(userInfoDTO, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @GetMapping("/user/{id}") //TODO make user detail
    public ResponseEntity<UserInfoDTO> getUserById(@PathVariable Long id) {
        UserInfoDTO userInfoById = userInfoService.getUserById(id);
        if (userInfoById == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(userInfoById, HttpStatus.OK);
    }


    @GetMapping("/users")//TODO make user detail
    public ResponseEntity<List<UserInfoDTO>> getAll(@RequestParam(name = "detail", defaultValue = "false") boolean detail) {
        userInfoService.createUser(new UserInfo("mike", "wazovsky", "123456789123", "someUuid"));   //TODO just for test
        if (detail) {
            List<UserInfoDTO> usersList = userInfoService.findAllUsersDetail();
            return new ResponseEntity<>(usersList, HttpStatus.OK);
        } else {
            List<UserInfoDTO> usersList = userInfoService.findAllUsers();
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
        if (userInfoService.getUserById(id) != null) {
            userInfoService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
