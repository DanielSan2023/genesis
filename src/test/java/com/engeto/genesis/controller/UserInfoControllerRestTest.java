package com.engeto.genesis.controller;

import com.engeto.genesis.domain.UserInfo;
import com.engeto.genesis.model.UserInfoDTO;
import com.engeto.genesis.repository.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.PUT;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserInfoControllerRestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @BeforeEach
    void setUp() {
        userInfoRepository.deleteAll();
    }

    @Test
    void GIVEN_empty_DB_WHEN_get_users_THEN_nothing_is_returned() {
        UserInfo[] userInfo = restTemplate.getForObject(
                "http://localhost:" + port + "/api/v1/users?detail=true", UserInfo[].class);

        assertThat(userInfo).isNullOrEmpty();
    }

    @Test
    void GIVEN_empty_DB_WHEN_get_users_without_detail_THEN_nothing_is_returned() {
        UserInfo[] userInfo = restTemplate.getForObject(
                "http://localhost:" + port + "/api/v1/users?detail=false", UserInfo[].class);

        assertThat(userInfo).isNullOrEmpty();
    }

    @Test
    void GIVEN_user_with_incorrect_personId_WHEN_createUser_endpoint_THEN_get_users_return_null() {
        //GIVEN
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .name("mike")
                .surname("wazovsky")
                .personId("123456") //to short
                .build();

        //WHEN
        ResponseEntity<Object> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/user", userInfoDTO, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        LinkedHashMap<String, String> responseBody = (LinkedHashMap) response.getBody();    //TODO find better solution

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.get("message"))
                .isEqualTo("PersonId length doesn't match. It needs to have exactly: 12 characters");

        //THEN
        UserInfo[] actual = restTemplate.getForObject(
                "http://localhost:" + port + "/api/v1/users?detail=true", UserInfo[].class);

        assertThat(actual).isNull();
    }

    @Test
    void GIVEN_saved_correct_user_info_dto_in_db_WHEN_get_user_detail_endpoint_THEN_saved_user_is_returned_and_checked() {
        //GIVEN
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .name("mike")
                .surname("wazovsky")
                .personId("123456789123")
                .build();
        ResponseEntity<UserInfoDTO> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/user", userInfoDTO, UserInfoDTO.class);
        Long userInfoId = Objects.requireNonNull(response.getBody()).getId();

        //WHEN
        UserInfo[] actual = restTemplate.getForObject(
                "http://localhost:" + port + "/api/v1/users?detail=true", UserInfo[].class);

        //THEN
        assertThat(actual).isNotEmpty().hasSize(1);
        assertThat(actual[0].getId()).isEqualTo(userInfoId);
        assertThat(actual[0].getName()).isEqualTo("mike");
        assertThat(actual[0].getSurname()).isEqualTo("wazovsky");
        assertThat(actual[0].getPersonId()).isEqualTo("123456789123");
        assertThat(actual[0].getUuid()).isNotEmpty();
    }

    @Test
    void GIVEN_user_info_dto_WHEN_saved_user_info_to_DB_THEN_saved_user_info_returned_checked() {
        //GIVEN
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .name("John")
                .surname("Doe")
                .personId("123456789321")
                .build();

        //WHEN
        ResponseEntity<UserInfoDTO> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/user", userInfoDTO, UserInfoDTO.class);

        //THEN
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getSurname()).isEqualTo(userInfoDTO.getSurname());
        assertThat(response.getBody().getPersonId()).isEqualTo(userInfoDTO.getPersonId());
        assertThat(userInfoRepository.findAll()).hasSize(1);
    }

    @Test
    void GIVEN_user_info_dto_WHEN_get_user_info_by_id_from_DB_THEN_checked_if_found_return_OK_Status() {
        //GIVEN
        UserInfo userInfo = userInfoRepository.save(new UserInfo("mike", "wazovsky", "123456789123", "someUuid"));

        //WHEN
        ResponseEntity<UserInfoDTO> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/user/{id}", UserInfoDTO.class, userInfo.getId());

        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(userInfo.getId());
    }

    @Test
    void GIVEN_saved_user_info_dto_to_DB_WHEN_update_user_by_Id_THEN_checked_user_info_DB_return_Ok_Status() {
        //GIVEN
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .name("John")
                .surname("Doe")
                .personId("123256789321")
                .build();
        UserInfoDTO updateUserInfoDTO = new UserInfoDTO();
        updateUserInfoDTO.setName("UpdatedName");
        updateUserInfoDTO.setSurname("UpdatedSurName");

        ResponseEntity<UserInfoDTO> savedUserInfoDTO = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/user", userInfoDTO, UserInfoDTO.class);
        Long userId = Objects.requireNonNull(savedUserInfoDTO.getBody()).getId();

        //WHEN
        ResponseEntity<HttpStatus> response = restTemplate.exchange(
                "http://localhost:" + port + "/api/v1/user/{id}", PUT, new HttpEntity<>(updateUserInfoDTO),
                HttpStatus.class, userId);

        Optional<UserInfo> UserOptional = userInfoRepository.findById(userId);
        UserInfo userInfoDTOFromDB = UserOptional.orElse(null);

        //THEN
        assertThat(userInfoRepository.findById(userId)).isPresent();
        assertThat(Objects.requireNonNull(userInfoDTOFromDB).getName()).isEqualTo("UpdatedName");
        assertThat(userInfoDTOFromDB.getSurname()).isEqualTo("UpdatedSurName");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void GIVEN_user_info_dto_with_incorrect_Id_WHEN_update_user_by_Id_THEN_return_NOT_FOUND_status() {
        //GIVEN
        Long incorrectId = 356L;
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .name("John")
                .surname("Doe")
                .personId("123256789321")
                .build();

        restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/user", userInfoDTO, UserInfoDTO.class);

        //WHEN
        ResponseEntity<HttpStatus> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/v1/user/{id}", HttpStatus.class, incorrectId);
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(userInfoRepository.findById(incorrectId)).isEmpty();
    }

    @Test
    void GIVEN_saved_correct_user_info_dto_to_DB_WHEN_delete_user_info_by_Id_THEN_checked_user_info_dto_by_Id_is_removed_from_DB_return_Ok_Status() {
        //GIVEN
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .name("John")
                .surname("Doe")
                .personId("123456785321")
                .build();

        ResponseEntity<UserInfoDTO> savedUserInfoDTO = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/user", userInfoDTO, UserInfoDTO.class);
        assertThat(userInfoRepository.findById(Objects.requireNonNull(savedUserInfoDTO.getBody()).getId())).isNotNull();

        //WHEN
        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + port + "/api/v1/user/{id}", HttpMethod.DELETE,
                null, Void.class, Objects.requireNonNull(savedUserInfoDTO.getBody()).getId());

        //THEN
        assertThat(userInfoRepository.findById(savedUserInfoDTO.getBody().getId())).isNotPresent();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}