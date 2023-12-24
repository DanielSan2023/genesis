package com.engeto.genesis.controller;

import com.engeto.genesis.domain.UserInfo;
import com.engeto.genesis.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserInfoControllerUnitTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void GIVEN_mocked_createUser_as_null_WHEN_createUser_is_called_THEN_INTERNAL_SERVER_ERROR_is_returned() {
        when(userService.createUser(any())).thenReturn(null);

        ResponseEntity<UserInfo> returnValue = userController.createUser(new UserInfo());

        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void GIVEN_mocked_createUser_as_created_object_WHEN_createUser_is_called_THEN_INTERNAL_SERVER_ERROR_is_returned() {
        when(userService.createUser(any())).thenReturn(new UserInfo());

        ResponseEntity<UserInfo> returnValue = userController.createUser(new UserInfo());

        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        UserInfo body = returnValue.getBody();
        assertThat(body).isNotNull();
    }
}