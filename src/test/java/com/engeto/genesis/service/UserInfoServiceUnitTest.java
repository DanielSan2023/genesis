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
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserInfoServiceUnitTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @InjectMocks
    private UserInfoService userInfoService;


    @Test
    public void GIVEN_userInfo_List_WHEN_findAllUsersDetail_THEN_return_userInfo_List_with_all_parameters() {
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
        // assertThat(resultList).hasSameElementsAs(mockUserInfosDTO);//TODO compare two list
        assertThat(resultList).hasSize(2);
    }

    public List<UserInfoDTO> convertDomainListToDTOList(List<UserInfo> userInfos) {
        return userInfos.stream()
                .map(userInfo -> new UserInfoDTO(userInfo.getName(), userInfo.getSurname(),
                        userInfo.getPersonId(), userInfo.getUuid()))
                .collect(Collectors.toList());
    }

    @Test
    public void GIVEN_userInfo_List_WHEN_findAllUsers_THEN_return_userInfo_List_without_detail() {
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
    public void GIVEN_userInfoDTO_WHEN_mapDTOToDomain_THEN_return_converted_userInfo_checked() {
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
    public void GIVEN_userInfo_WHEN_getUserByIdDetail_THEN_return_savedUserInfo_with_detail_checked() {
        //GIVEN
        Long userId = 1L;
        UserInfo userInfo = (new UserInfo("mike", "wazovsky", "123456789123", "someUuid"));
        when(userInfoRepository.findById(userId)).thenReturn(Optional.of(userInfo));

        //WHEN
        UserInfoDTO resultUserInfoDTO = userInfoService.getUserByIdDetail(userId);

        //THEN
        assertThat(resultUserInfoDTO).isNotNull();
        assertThat(resultUserInfoDTO.getName()).isEqualTo(userInfo.getName());
        assertThat(resultUserInfoDTO.getSurname()).isEqualTo(userInfo.getSurname());
        assertThat(resultUserInfoDTO.getPersonId()).isEqualTo(userInfo.getPersonId());
        assertThat(resultUserInfoDTO.getUuid()).isEqualTo(userInfo.getUuid());
    }

    @Test
    public void GIVEN_userInfo_WHEN_getUserByIdDetail_THEN_returned_null() {
        //GIVEN
        Long userId = 100L;
        when(userInfoRepository.findById(userId)).thenReturn(Optional.empty());

        //WHEN
        UserInfoDTO resultUserInfoDTO = userInfoService.getUserByIdDetail(userId);

        //THEN
        assertThat(resultUserInfoDTO).isNull();
    }

    @Test
    public void GIVEN_userInfo_WHEN_getUserById_THEN_return_savedUserInfo_without_detail_checked() {
        //GIVEN
        Long userId = 1L;
        UserInfo userInfo = (new UserInfo("mike", "wazovsky", "123456789123", "someUuid"));
        when(userInfoRepository.findById(userId)).thenReturn(Optional.of(userInfo));

        //WHEN
        UserInfoDTO resultUserInfoDTO = userInfoService.getUserById(userId);

        //THEN
        assertThat(resultUserInfoDTO).isNotNull();
        assertThat(resultUserInfoDTO.getName()).isEqualTo(userInfo.getName());
        assertThat(resultUserInfoDTO.getSurname()).isEqualTo(userInfo.getSurname());
        assertThat(resultUserInfoDTO.getPersonId()).isNull();
        assertThat(resultUserInfoDTO.getUuid()).isNull();
    }

    @Test
    public void GIVEN_userInfo_WHEN_getUserById_THEN_returned_null() {
        //GIVEN
        Long userId = 100L;
        when(userInfoRepository.findById(userId)).thenReturn(Optional.empty());

        //WHEN
        UserInfoDTO resultUserInfoDTO = userInfoService.getUserById(userId);

        //THEN
        assertThat(resultUserInfoDTO).isNull();
    }

    @Test
    void GIVEN_mocked_userInfo_in_DB_WHEN_getUserById_THEN_return_dto_with_removed_personId_Uuid() {
        //GIVEN
        UserInfo userInfo = new UserInfo("mike", "wazovsky", "123456789123", "someUuid");
        long id = 4L;
        userInfo.setId(id);
        when(userInfoRepository.findById(id)).thenReturn(Optional.of(userInfo));

        //WHEN
        UserInfoDTO dto = userInfoService.getUserById(id);

        //THEN
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(userInfo.getId());
        assertThat(dto.getName()).isEqualTo(userInfo.getName());
        assertThat(dto.getSurname()).isEqualTo(userInfo.getSurname());
        assertThat(dto.getPersonId()).isNull();
        assertThat(dto.getUuid()).isNull();
    }

    @Test
    void GIVEN_empty_DB_WHEN_getUserById_THEN_return_null() {
        //GIVEN
        long id = 4L;
        when(userInfoRepository.findById(id)).thenReturn(Optional.empty());

        //WHEN
        UserInfoDTO dto = userInfoService.getUserById(id);

        //THEN
        assertThat(dto).isNull();
    }

    @Test
    void GIVEN_person_id_WHEN_createUser_THEN_save_is_called_once() {
        //GIVEN
        String personId = "852963821654";
        UserInfoDTO userInfoDTO = new UserInfoDTO("Tom", "Tailor", personId, "someUuid");
        when(userInfoRepository.existsByPersonIdIgnoreCase(personId)).thenReturn(false);

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDTO, userInfo);
        when(userInfoRepository.save(Mockito.any())).thenReturn(userInfo);

        //WHEN
        UserInfoDTO dto = userInfoService.createUser(userInfoDTO);

        //THEN
        Mockito.verify(userInfoRepository, times(1)).save(any());
    }

    @Test
    void GIVEN_db_with_existing_person_id_WHEN_createUser_is_called_THEN_save_is_not_called() {
        //GIVEN
        String personId = "852963821654";
        UserInfoDTO userInfoDTO = createUserInfo(personId);
        when(userInfoRepository.existsByPersonIdIgnoreCase(personId)).thenReturn(true);

        //WHEN
        assertThrows(RuntimeException.class, () -> userInfoService.createUser(userInfoDTO));

        //THEN
        Mockito.verify(userInfoRepository, times(0)).save(any());
    }

    private UserInfoDTO createUserInfo(String personId) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setPersonId(personId);

        return userInfoDTO;
    }

    @Test
    public void test_max_Length_Person_Id_with_correct_Id_Length() {
        //GIVEN
        String personId = "852963821611656554";
        UserInfoDTO userInfoDTO = new UserInfoDTO("Tom", "Tailor", personId, "someUuid");

        //WHEN
        assertThrows(RuntimeException.class, () -> userInfoService.createUser(userInfoDTO));

        //THEN
        Mockito.verify(userInfoRepository, times(0)).save(any());
    }

    @Test
    public void test_min_Length_Person_Id_with_correct_Id_Length() {
        //GIVEN
        String personId = "85254";
        UserInfoDTO userInfoDTO = new UserInfoDTO("Tom", "Tailor", personId, "someUuid");

        //WHEN
        assertThrows(RuntimeException.class, () -> userInfoService.createUser(userInfoDTO));

        //THEN
        Mockito.verify(userInfoRepository, times(0)).save(any());
    }

    @Test
    public void GIVEN_userInfo_WHEN_updateUserById_THEN_saved_DB() {
        //GIVEN
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setName("Tom");
        userInfoDTO.setSurname("Cruise");

        UserInfoDTO existUserInfoDTO = new UserInfoDTO("OldName", "OldSurname", "951357852654", "someUuid");
        existUserInfoDTO.setId(5L);

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(existUserInfoDTO, userInfo);
        when(userInfoRepository.findById(5L)).thenReturn(Optional.of(userInfo));

        //WHEN
        userInfoService.updateUserById(5L, userInfoDTO);
        assertThat(userInfo.getName()).isEqualTo(userInfoDTO.getName());
        assertThat(userInfo.getSurname()).isEqualTo(userInfoDTO.getSurname());
        assertThat(userInfo.getPersonId()).isEqualTo(existUserInfoDTO.getPersonId());
        assertThat(userInfo.getUuid()).isEqualTo(existUserInfoDTO.getUuid());

        //THEN
        verify(userInfoRepository, times(1)).save(any());
    }

    @Test
    public void GIVEN_userInfo_WHEN_updateUserById_THEN_will_not_saved_DB() {
        //GIVEN
        long userId = 2L;
        UserInfoDTO existUserInfoDTO = new UserInfoDTO("OldName", "OldSurname", "951357852654", "someUuid");

        when(userInfoRepository.findById(2L)).thenReturn(Optional.empty());

        //WHEN
        userInfoService.updateUserById(userId, existUserInfoDTO);

        //THEN
        verify(userInfoRepository, never()).save(any());
    }

    @Test
    void GIVEN_userInfo_Id_WHEN_delete_userInfo_THEN_remove_userInfo() {
        //GIVEN
        long userId = 2L;

        doNothing().when(userInfoRepository).deleteById(userId);

        //WHEN
        userInfoService.delete(userId);

        //THEN
        verify(userInfoRepository, times(1)).deleteById(any());
    }
}
