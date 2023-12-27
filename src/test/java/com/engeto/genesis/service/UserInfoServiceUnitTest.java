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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserInfoServiceUnitTest {


    public static final int WANTED_NUMBER_OF_INVOCATIONS = 2;
    public static final int EXPECTED_NUMBER_OF_SIZE = 2;
    @Mock
    private UserInfoRepository userInfoRepository;

    @InjectMocks
    private UserInfoService userInfoService;

    @Test
    void GIVEN_db_with_non_existing_person_id_WHEN_createUser_is_called_THEN_save_is_called_once() {
        //GIVEN
        String personId = "somePersonId";
        UserInfo userInfo = createUserInfo(personId);

        when(userInfoRepository.existsByPersonIdIgnoreCase(personId)).thenReturn(false);

        //WHEN
        userInfoService.createUser(userInfo);

        //THEN
        Mockito.verify(userInfoRepository, times(1)).save(userInfo);
    }

    @Test
    void GIVEN_db_with_existing_person_id_WHEN_createUser_is_called_THEN_save_is_not_called() {
        //GIVEN
        String personId = "somePersonId";
        UserInfo userInfo = createUserInfo(personId);

        when(userInfoRepository.existsByPersonIdIgnoreCase(personId)).thenReturn(true);

        //WHEN
        userInfoService.createUser(userInfo);

        //THEN
        Mockito.verify(userInfoRepository, times(0)).save(userInfo);
    }

    private UserInfo createUserInfo(String personId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setPersonId(personId);

        return userInfo;
    }

    //TODO add test for testing MAX_LENGTH_PERSON_ID

    @Test
    public void testFindAllUsersDetail() {
        UserInfo userInfo1 = (new UserInfo("mike", "wazovsky", "123456789123", "someUuid"));
        UserInfo userInfo2 = (new UserInfo("jerry", "jetsky", "123456789123", "someUuid"));
        List<UserInfo> mockUserInfos = Arrays.asList(userInfo1, userInfo2);

        when(userInfoRepository.findAll(Sort.by("id"))).thenReturn(mockUserInfos);
        List<UserInfoDTO> resultList = userInfoService.findAllUsersDetail();

        // Verify that the repository method was called with the correct parameters
        verify(userInfoRepository).findAll(Sort.by("id"));

        assertEquals(EXPECTED_NUMBER_OF_SIZE, resultList.size());
        verify(userInfoService, times(WANTED_NUMBER_OF_INVOCATIONS)).convertToDTO(any(UserInfo.class), any(UserInfoDTO.class));
    }



}
