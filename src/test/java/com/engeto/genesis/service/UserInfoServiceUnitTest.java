package com.engeto.genesis.service;

import com.engeto.genesis.domain.UserInfo;
import com.engeto.genesis.model.UserInfoDTO;
import com.engeto.genesis.repository.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserInfoServiceUnitTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @InjectMocks
    private UserInfoService userInfoService;

//    @Test
//    void GIVEN_db_with_non_existing_person_id_WHEN_createUser_is_called_THEN_save_is_called_once() {
//        //GIVEN
//        String personId = "somePersonId";
//        UserInfoDTO userInfoDTO = createUserInfo(personId);
//        when(userInfoRepository.existsByPersonIdIgnoreCase(personId)).thenReturn(false);
//
//        //WHEN
//        userInfoService.createUser(userInfoDTO);
//
//        //THEN
//        Mockito.verify(userInfoRepository, times(1)).save(new UserInfo());
//    }
//
//    @Test
//    void GIVEN_db_with_existing_person_id_WHEN_createUser_is_called_THEN_save_is_not_called() {
//        //GIVEN
//        String personId = "somePersonId";
//        UserInfoDTO userInfoDTO = createUserInfo(personId);
//        when(userInfoRepository.existsByPersonIdIgnoreCase(personId)).thenReturn(true);
//
//        //WHEN
//        userInfoService.createUser(userInfoDTO);
//
//        //THEN
//        Mockito.verify(userInfoRepository, times(0)).save(new UserInfo());
//    }
//
//    private UserInfoDTO createUserInfo(String personId) {
//        UserInfoDTO userInfoDTO = new UserInfoDTO();
//        userInfoDTO.setPersonId(personId);
//
//        return userInfoDTO;
//    }

    @Test
    public void GIVEN_userInfo_List_WHEN_called_findAllUsersDetail_THEN_return_userInfo_List_with_all_parameters() {
        //GIVEN
        UserInfo userInfo1 = (new UserInfo("mike", "wazovsky", "123456789123", "someUuid"));
        UserInfo userInfo2 = (new UserInfo("jerry", "jetsky", "123456789123", "someUuid"));
        List<UserInfo> mockUserInfos = Arrays.asList(userInfo1, userInfo2);
        List<UserInfoDTO> mockUserInfosDTO = convertDomainListToDTOList(mockUserInfos);

        //WHEN
        when(userInfoRepository.findAll(Sort.by("id"))).thenReturn(mockUserInfos);
        List<UserInfoDTO> resultList = userInfoService.findAllUsersDetail();

        //THEN
        verify(userInfoRepository).findAll(Sort.by("id"));
        //  assertThat(new ArrayList<>(resultList)).isEqualTo(mockUserInfosDTO); //TODO compare two list
        assertThat(resultList).hasSize(2);
    }

    public List<UserInfoDTO> convertDomainListToDTOList(List<UserInfo> userInfos) {
        return userInfos.stream()
                .map(userInfo -> new UserInfoDTO(userInfo.getName(), userInfo.getSurname(),
                        userInfo.getPersonId(), userInfo.getUuid()))
                .collect(Collectors.toList());
    }

    @Test
    public void GIVEN_userInfo_List_WHEN_called_findAllUsers_THEN_return_userInfo_List_without_detail() {
        //GIVEN
        UserInfo userInfo1 = (new UserInfo("mike", "wazovsky", "123456789123", "someUuid"));
        UserInfo userInfo2 = (new UserInfo("jerry", "jetsky", "123456789123", "someUuid"));
        List<UserInfo> mockUserInfos = Arrays.asList(userInfo1, userInfo2);

        //WHEN
        when(userInfoRepository.findAll(Sort.by("id"))).thenReturn(mockUserInfos);
        List<UserInfoDTO> resultList = userInfoService.findAllUsers();

        Optional<UserInfoDTO> optionalUserByNameMike = resultList
                .stream().filter(userInfoDTO -> ("mike").equals(userInfoDTO.getName())).findFirst();
        Optional<UserInfoDTO> optionalUserByNameJerry = resultList
                .stream().filter(userInfoDTO -> ("jerry").equals(userInfoDTO.getName())).findFirst();

        assertThat(optionalUserByNameMike).isPresent();
        assertThat(optionalUserByNameJerry).isPresent();

        UserInfoDTO firstCheckedUserInfoDTO = optionalUserByNameMike.get();
        UserInfoDTO secondCheckedUserInfoDTO = optionalUserByNameJerry.get();

        //THEN
        verify(userInfoRepository).findAll(Sort.by("id"));
        assertThat(resultList).hasSize(2);

        assertThat(firstCheckedUserInfoDTO.getPersonId()).isNull();
        assertThat(firstCheckedUserInfoDTO.getUuid()).isNull();

        assertThat(secondCheckedUserInfoDTO.getPersonId()).isNull();
        assertThat(secondCheckedUserInfoDTO.getUuid()).isNull();
    }

    @Test
    public void GIVEN_userInfoDTO_WHEN_called_mapDTOToDomain_THEN_return_converted_userinfo_checked() {
        //GIVEN
        UserInfoDTO mockDTO = mock(UserInfoDTO.class);
        when(mockDTO.getId()).thenReturn(1L);
        when(mockDTO.getName()).thenReturn("John");
        when(mockDTO.getSurname()).thenReturn("Doe");

        // WHEN
        UserInfo result = userInfoService.mapDTOToDomain(mockDTO);

        // THEN
        assertThat(result.getId()).isEqualTo(mockDTO.getId());
        assertThat(result.getName()).isEqualTo(mockDTO.getName());
        assertThat(result.getSurname()).isEqualTo(mockDTO.getSurname());
    }

    @Test
    public void test_max_Length_Person_Id_with_correct_Id_Length() {
        int expectedLength = 12;
        int actualMaxLength = UserInfoService.MAX_LENGTH_PERSON_ID;

        assertThat(actualMaxLength).isEqualTo(expectedLength);
    }

}
