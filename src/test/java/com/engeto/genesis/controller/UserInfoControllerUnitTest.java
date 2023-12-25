package com.engeto.genesis.controller;

import com.engeto.genesis.domain.UserInfo;
import com.engeto.genesis.model.UserInfoDTO;
import com.engeto.genesis.service.UserInfoService;
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
    private UserInfoService userInfoService;

    @InjectMocks
    private UserController userController;

    @Test
    void GIVEN_mocked_createUser_as_null_WHEN_createUser_is_called_THEN_INTERNAL_SERVER_ERROR_is_returned() {
        // Konfigurácia mocka na simuláciu chyby (vrátenie null)
        when(userInfoService.createUser(any())).thenReturn(null);

        // Zavolať metódu v UserController
        ResponseEntity<UserInfoDTO> returnValue = userController.createUser(new UserInfoDTO());

        // Overiť, či výsledný stav a telo sú v súlade s očakávaním
        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(returnValue.getBody()).isNull();
    }

    @Test
    void GIVEN_mocked_createUser_as_created_object_WHEN_createUser_is_called_THEN_CREATED_status_and_non_null_body_is_returned() {
        // Konfigurácia mocka na simuláciu úspešného vytvorenia
        when(userInfoService.createUser(any())).thenReturn(new UserInfo());

        // Zavolať metódu v UserController
        ResponseEntity<UserInfoDTO> returnValue = userController.createUser(new UserInfoDTO());

        // Overiť, či výsledný stav a objekt nie sú null
        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(returnValue.getBody()).isNotNull();
    }



}