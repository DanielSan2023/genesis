package com.engeto.genesis.controller;

import com.engeto.genesis.model.UserInfoDTO;
import com.engeto.genesis.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserInfoControllerUnitTest {

    public static final int WANTED_NUMBER_OF_INVOCATIONS = 1;
    @Mock
    private UserInfoService userInfoService;

    @InjectMocks
    private UserController userController;

    @Test
    void GIVEN_mocked_createUser_as_null_WHEN_createUser_is_called_THEN_INTERNAL_SERVER_ERROR_is_returned() {
        //GIVEN
        when(userInfoService.createUser(any())).thenReturn(null);

        //WHEN
        ResponseEntity<UserInfoDTO> returnValue = userController.createUser(new UserInfoDTO());

        //THEN
        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(returnValue.getBody()).isNull();
    }

    @Test
    void GIVEN_mocked_createUser_as_created_object_WHEN_createUser_is_called_THEN_CREATED_status_and_non_null_body_is_returned() {
        //GIVEN
        when(userInfoService.createUser(any())).thenReturn(new UserInfoDTO());

        //WHEN
        ResponseEntity<UserInfoDTO> returnValue = userController.createUser(new UserInfoDTO());

        //THEN
        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(returnValue.getBody()).isNotNull();
    }

    @Test
    void GIVEN_mocked_getUser_By_Id_as_null_object_When_getUserById_is_called_THEN_NOT_FOUND_is_returned() {
        //GIVEN
        Long userInfoId = 1L;
        when(userInfoService.getUserById(userInfoId)).thenReturn(null);

        //WHEN
        ResponseEntity<UserInfoDTO> returnValue = userController.getUserById(userInfoId, false);

        //THEN
        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(returnValue.getBody()).isNull();
    }

    @Test
    void GIVEN_mocked_getUser_By_Id_as_receive_object_WHEN_getUserById_is_called_THEN_CREATED_status_and_non_null_body_is_returned() {
        //GIVEN
        Long userInfoId = 1L;
        when(userInfoService.getUserById(userInfoId)).thenReturn(new UserInfoDTO());

        //WHEN
        ResponseEntity<UserInfoDTO> returnValue = userController.getUserById(userInfoId, false);

        //THEN
        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(returnValue.getBody()).isNotNull();
    }

    @Test
    void GIVEN_mocked_getUser_By_Id_detail_as_null_object_When_getUserById_is_called_THEN_NOT_FOUND_is_returned() {
        //GIVEN
        Long userInfoId = 1L;
        when(userInfoService.getUserByIdDetail(userInfoId)).thenReturn(null);

        //WHEN
        ResponseEntity<UserInfoDTO> returnValue = userController.getUserById(userInfoId, true);

        //THEN
        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(returnValue.getBody()).isNull();
    }

    @Test
    void GIVEN_mocked_getUser_By_Id_detail_as_receive_object_WHEN_getUserById_is_called_THEN_CREATED_status_and_non_null_body_is_returned() {
        //GIVEN
        Long userInfoId = 1L;
        when(userInfoService.getUserByIdDetail(userInfoId)).thenReturn(new UserInfoDTO());

        //WHEN
        ResponseEntity<UserInfoDTO> returnValue = userController.getUserById(userInfoId, true);

        //THEN
        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(returnValue.getBody()).isNotNull();
    }

    @Test
    void GIVEN_mocked_getAllUsers_Detail_as_null_WHEN_findAllUsers_is_called_THEN_NOT_FOUND_is_returned() {
        //GIVEN
        when(userInfoService.findAllUsersDetail()).thenReturn(null);

        //WHEN
        ResponseEntity<List<UserInfoDTO>> userInfosList = userController.getAll(true);

        //THEN
        assertThat(userInfosList.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(userInfosList.getBody()).isNull();
    }

    @Test
    void GIVEN_mocked_getAllUser_Detail_as_receive_List_UserInfo_WHEN_findAllUsers_is_called_THEN_CREATED_status_and_non_null_body_is_returned() {
        //GIVEN
        UserInfoDTO userInfoDTO1 = new UserInfoDTO();
        UserInfoDTO userInfoDTO2 = new UserInfoDTO();
        List<UserInfoDTO> userInfoDTOList = Arrays.asList(userInfoDTO1, userInfoDTO2);
        when(userInfoService.findAllUsersDetail()).thenReturn(userInfoDTOList);

        //WHEN
        ResponseEntity<List<UserInfoDTO>> userInfosList = userController.getAll(true);

        //THEN
        assertThat(userInfosList.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userInfosList.getBody()).isNotNull();
    }

    @Test
    void GIVEN_mocked_getAllUsers_as_null_WHEN_findAllUsers_is_called_THEN_NOT_FOUND_is_returned() {
        //GIVEN
        when(userInfoService.findAllUsers()).thenReturn(null);

        //WHEN
        ResponseEntity<List<UserInfoDTO>> userInfosList = userController.getAll(false);

        //THEN
        assertThat(userInfosList.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(userInfosList.getBody()).isNull();
    }

    @Test
    void GIVEN_mocked_getAllUser_as_receive_List_UserInfo_WHEN_findAllUsers_is_called_THEN_CREATED_status_and_non_null_body_is_returned() {
        //GIVEN
        UserInfoDTO userInfoDTO1 = new UserInfoDTO();
        UserInfoDTO userInfoDTO2 = new UserInfoDTO();
        List<UserInfoDTO> userInfoDTOList = Arrays.asList(userInfoDTO1, userInfoDTO2);
        when(userInfoService.findAllUsers()).thenReturn(userInfoDTOList);

        //WHEN
        ResponseEntity<List<UserInfoDTO>> userInfosList = userController.getAll(false);

        //THEN
        assertThat(userInfosList.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userInfosList.getBody()).isNotNull();
    }

    @Test
    void GIVEN_mocked_existing_user_WHEN_updateUserById_is_called_THEN_OK_status_is_returned() {
        //GIVEN
        Long userInfoId = 1L;
        UserInfoDTO existUserInfoDTO = new UserInfoDTO();
        when(userInfoService.getUserById(userInfoId)).thenReturn(existUserInfoDTO);

        //WHEN
        ResponseEntity<HttpStatus> responseEntity = userController.updateUserById(userInfoId, new UserInfoDTO());

        //THEN
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }//TODO adjust

    @Test
    void GIVEN_mocked_existing_user_WHEN_updateUserById_is_called_THEN_NOT_FOUND_status_is_returned() {
        //GIVEN
        Long userInfoId = 1L;
        when(userInfoService.getUserById(userInfoId)).thenReturn(null);

        //WHEN
        ResponseEntity<HttpStatus> responseEntity = userController.updateUserById(userInfoId, null);

        //THEN
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }//TODO adjust

    @Test
    void GIVEN_mocked_existing_user_WHEN_deleteUser_is_called_THEN_OK_status_is_returned() {
        //GIVEN
        Long userId = 1L;
        doNothing().when(userInfoService).delete(userId);

        //WHEN
        ResponseEntity<HttpStatus> responseStatus = userController.deleteUser(userId);

        //THEN
        assertThat(responseStatus.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userInfoService, times(WANTED_NUMBER_OF_INVOCATIONS)).delete(userId);
    }

}