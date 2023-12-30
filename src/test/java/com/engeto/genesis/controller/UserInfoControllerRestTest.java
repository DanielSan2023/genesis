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


import static io.restassured.RestAssured.port;
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
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .name("John")
                .surname("Doe")
                .personId("123456789321")
                .build();

        ResponseEntity<UserInfoDTO> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/v1/user", userInfoDTO, UserInfoDTO.class);
        assertThat(response.getBody().getSurname()).isEqualTo(userInfoDTO.getSurname());
        assertThat(response.getBody().getPersonId()).isEqualTo(userInfoDTO.getPersonId());

        assertThat(userInfoRepository.findAll()).hasSize(1);
    }


@Test
void getUserByIdShouldReturnUserInfo() {
    Long userId = 1L;
    UserInfo userInfo = userInfoRepository.save(new UserInfo("mike", "wazovsky", "123456789123", "someUuid"));

    ResponseEntity<UserInfoDTO> response = restTemplate.getForEntity(
            "http://localhost:" + port + "/api/v1/user/{id}", UserInfoDTO.class, userInfo.getId());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getId()).isEqualTo(userInfo.getId());
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