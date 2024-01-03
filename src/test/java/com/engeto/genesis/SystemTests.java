package com.engeto.genesis;

import com.engeto.genesis.model.UserInfoDTO;
import com.engeto.genesis.repository.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SystemTests {

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
    void GIVEN_empty_DB_WHEN__THEN_() {
//        createUser
//        getUserById
//        updateUserById
//        getAll  //with details
//        deleteUser
//        getAll  //without details
    }

    @Test
    public void testCreateReadDelete() {
        UserInfoDTO oneUserInfoDTO = new UserInfoDTO("Jack", "Johnson", "654321987456", "someUuid");
        ResponseEntity<UserInfoDTO> createdUserInfoDTO = restTemplate.postForEntity("http://localhost:" + port + "/api/v1/user", oneUserInfoDTO, UserInfoDTO.class);

        UserInfoDTO[] userInfoDTODetail = restTemplate.getForObject("http://localhost:" + port + "/api/v1/users?detail=true", UserInfoDTO[].class);
        assertThat(userInfoDTODetail).extracting(UserInfoDTO::getName).containsOnly("Jack");

        restTemplate.delete("http://localhost:" + port + "/api/v1/user" + "/" + userInfoDTODetail[0].getId());
        assertThat(restTemplate.getForObject("http://localhost:" + port + "/api/v1/users?detail=true", UserInfoDTO[].class)).isEmpty();
    }

    @Test
    public void testErrorHandlingReturnsBadRequest() {
        String url = "http://localhost:" + port + "/wrong";

        try {
            restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }

}
