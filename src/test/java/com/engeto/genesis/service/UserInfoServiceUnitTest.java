package com.engeto.genesis.service;

import com.engeto.genesis.domain.UserInfo;
import com.engeto.genesis.model.UserInfoDTO;
import com.engeto.genesis.repository.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserInfoServiceUnitTest {

    public static final int WANTED_NUMBER_OF_INVOCATIONS = 2;
    @Mock
    private UserInfoRepository userInfoRepository;

    @InjectMocks
    private UserInfoService userInfoService;

    @Test
    void GIVEN_db_with_non_existing_person_id_WHEN_createUser_is_called_THEN_save_is_called_once() {
        //GIVEN
        String personId = "somePersonId";
        UserInfoDTO userInfoDTO = createUserInfo(personId);
        when(userInfoRepository.existsByPersonIdIgnoreCase(personId)).thenReturn(false);

        //WHEN
        userInfoService.createUser(userInfoDTO);

        //THEN
        Mockito.verify(userInfoRepository, times(1)).save(new UserInfo());
    }

    @Test
    void GIVEN_db_with_existing_person_id_WHEN_createUser_is_called_THEN_save_is_not_called() {
        //GIVEN
        String personId = "somePersonId";
        UserInfoDTO userInfoDTO = createUserInfo(personId);
        when(userInfoRepository.existsByPersonIdIgnoreCase(personId)).thenReturn(true);

        //WHEN
        userInfoService.createUser(userInfoDTO);

        //THEN
        Mockito.verify(userInfoRepository, times(0)).save(new UserInfo());
    }

    private UserInfoDTO createUserInfo(String personId) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setPersonId(personId);

        return userInfoDTO;
    }

    @Test
    public void test_max_Length_Person_Id_with_correct_Id_Length() {
        int expectedLength = 12;
        int actualMaxLength = UserInfoService.MAX_LENGTH_PERSON_ID;

        assertThat(actualMaxLength).isEqualTo(expectedLength);
    }

    @Test
    public void testFindAllUsersDetail() {
        //GIVEN
        UserInfo userInfo1 = (new UserInfo("mike", "wazovsky", "123456789123", "someUuid"));
        UserInfo userInfo2 = (new UserInfo("jerry", "jetsky", "123456789123", "someUuid"));
        List<UserInfo> mockUserInfos = Arrays.asList(userInfo1, userInfo2);

        //WHEN
        when(userInfoRepository.findAll(Sort.by("id"))).thenReturn(mockUserInfos);
        List<UserInfoDTO> resultList = userInfoService.findAllUsersDetail();

        //THEN
        verify(userInfoRepository).findAll(Sort.by("id"));
        verify(userInfoService, times(WANTED_NUMBER_OF_INVOCATIONS)).convertDomainToDTO(any(UserInfo.class), any(UserInfoDTO.class));
    }




}
