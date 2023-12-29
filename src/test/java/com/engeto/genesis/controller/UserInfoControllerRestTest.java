package com.engeto.genesis.controller;

import com.engeto.genesis.domain.UserInfo;
import com.engeto.genesis.model.UserInfoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.PUT;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserInfoControllerRestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void greetingShouldReturnDefaultMessage() {

        UserInfo[] actual = restTemplate.getForObject(
                "http://localhost:" + port + "/api/v1/users?detail=true", UserInfo[].class);

        assertThat(actual[0].getId()).isEqualTo(1);
        assertThat(actual[0].getName()).isEqualTo("mike");
        assertThat(actual[0].getSurname()).isEqualTo("wazovsky");
        assertThat(actual[0].getPersonId()).isEqualTo("123456789123");
        assertThat(actual[0].getUuid()).isNotEmpty();
    }

    @Test
    void createUserShouldReturnCreatedStatus() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setName("John");
        userInfoDTO.setSurname("Doe");
        userInfoDTO.setPersonId("123456789321");

        ResponseEntity<UserInfoDTO> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/user", userInfoDTO, UserInfoDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(userInfoDTO.getName());
        assertThat(response.getBody().getSurname()).isEqualTo(userInfoDTO.getSurname());
        assertThat(response.getBody().getPersonId()).isEqualTo(userInfoDTO.getPersonId());

    }

    @Test
    void getUserByIdShouldReturnUserInfo() {

        Long userId = 1L;

        ResponseEntity<UserInfoDTO> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/user/{id}", UserInfoDTO.class, userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(userId);
    }


    //    @Test
//    void updateUserByIdShouldReturnOkStatus() { //TODO make update Test
//        Long userId = 1L;
//
//        UserInfoDTO updateUserInfoDTO = new UserInfoDTO();
//        updateUserInfoDTO.setName("UpdatedName");
//
//        ResponseEntity<HttpStatus> response = restTemplate.exchange(
//                "http://localhost:" + port + "/api/v1/user/{id}", PUT, new HttpEntity<>(updateUserInfoDTO),
//                HttpStatus.class, userId);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//    }
    @Test
    void deleteUserShouldReturnOkStatus() {
        Long userId = 1L;

        ResponseEntity<Void> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/user/{id}", HttpMethod.DELETE, null, Void.class, userId);


        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }


}