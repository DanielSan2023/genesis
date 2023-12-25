package com.engeto.genesis.controller;

import com.engeto.genesis.domain.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserInfoControllerRestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void greetingShouldReturnDefaultMessage() {

        UserInfo[] actual = restTemplate.getForObject("http://localhost:" + port + "/api/v1/users", UserInfo[].class);

        assertThat(actual[0].getId()).isEqualTo(1);
        assertThat(actual[0].getName()).isEqualTo("mike");
        assertThat(actual[0].getSurname()).isEqualTo("wazovsky");
        assertThat(actual[0].getPersonId()).isEqualTo("123456789123");
        assertThat(actual[0].getUuid()).isEqualTo("someUuid");
    }

}