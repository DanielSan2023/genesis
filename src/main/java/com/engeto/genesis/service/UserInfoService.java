package com.engeto.genesis.service;


import com.engeto.genesis.domain.UserInfo;


import com.engeto.genesis.model.UserInfoDTO;
import com.engeto.genesis.repository.UserInfoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {
    public static final int MAX_LENGTH_PERSON_ID = 12;
    private final UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }


    public List<UserInfoDTO> findAllUsersDetail() {
        final List<UserInfo> userInfoes = userInfoRepository.findAll(Sort.by("id"));
        return userInfoes.stream()
                .map(userInfo -> convertToDTO(userInfo, new UserInfoDTO()))
                .toList();
    }

    public List<UserInfoDTO> findAllUsers() {
        final List<UserInfo> userInfoes = userInfoRepository.findAll(Sort.by("id"));
        return userInfoes.stream()
                .map(userInfo -> convertToDTO(userInfo, new UserInfoDTO()))
                .toList();
    }


    private UserInfoDTO convertToDTO(final UserInfo userInfo, final UserInfoDTO userInfoDTO) {
        userInfoDTO.setId(userInfo.getId());
        userInfoDTO.setName(userInfo.getName());
        userInfoDTO.setSurname(userInfo.getSurname());
        userInfoDTO.setPersonId(userInfo.getPersonId());
        userInfoDTO.setUuid(userInfo.getUuid());
        return userInfoDTO;
    }


    private UserInfo mapToDomain(final UserInfoDTO userInfoDTO) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userInfoDTO.getId());
        userInfo.setName(userInfoDTO.getName());
        userInfo.setSurname(userInfoDTO.getSurname());
        return userInfo;
    }


    public UserInfoDTO getUserById(Long id) {
        UserInfo userInfoById = userInfoRepository.findById(id).orElse(null);
        return convertToDTO(userInfoById, new UserInfoDTO());
    }


    public UserInfo createUser(UserInfo userInfo) {
        String personId = userInfo.getPersonId();
        if (personId.length() == MAX_LENGTH_PERSON_ID && !(userInfoRepository.existsByPersonIdIgnoreCase(personId))) {
            return userInfoRepository.save(userInfo);
        } else {
            return null;
        }
    }


    public void updateUserById(Long id, UserInfoDTO userInfoDTO) {
        UserInfo convertUserInfo = mapToDomain(userInfoDTO);
        userInfoRepository.findById(id).ifPresent(existingUserInfo -> {
            existingUserInfo.setName(convertUserInfo.getName());
            existingUserInfo.setSurname(convertUserInfo.getSurname());
            userInfoRepository.save(existingUserInfo);
        });

    }

    public void delete(Long id) {
        userInfoRepository.deleteById(id);
    }


}