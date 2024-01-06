package com.engeto.genesis.service;

import com.engeto.genesis.domain.UserInfo;
import com.engeto.genesis.model.UserInfoDTO;
import com.engeto.genesis.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Slf4j
@Service
public class UserInfoService {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoService.class);

    public static final int MAX_LENGTH_PERSON_ID = 12;
    private final UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public List<UserInfoDTO> findAllUsersDetail() {
        final List<UserInfo> userInfos = userInfoRepository.findAll(Sort.by("id"));
        return userInfos.stream()
                .map(userInfo -> convertDomainToDTO(userInfo, new UserInfoDTO()))
                .toList();
    }

    public List<UserInfoDTO> findAllUsers() {
        final List<UserInfo> userInfos = userInfoRepository.findAll(Sort.by("id"));
        userInfos.forEach(user -> {
            user.setPersonId(null);
            user.setUuid(null);
        });
        return userInfos.stream()
                .map(userInfo -> convertDomainToDTO(userInfo, new UserInfoDTO()))
                .toList();
    }

    UserInfoDTO convertDomainToDTO(final UserInfo userInfo, final UserInfoDTO userInfoDTO) {
        userInfoDTO.setId(userInfo.getId());
        userInfoDTO.setName(userInfo.getName());
        userInfoDTO.setSurname(userInfo.getSurname());
        userInfoDTO.setPersonId(userInfo.getPersonId());
        userInfoDTO.setUuid(userInfo.getUuid());
        return userInfoDTO;
    }

    UserInfo mapDTOToDomain(final UserInfoDTO userInfoDTO) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userInfoDTO.getId());
        userInfo.setName(userInfoDTO.getName());
        userInfo.setSurname(userInfoDTO.getSurname());
        return userInfo;
    }

    public UserInfoDTO getUserByIdDetail(Long id) {
        UserInfo userInfoById = userInfoRepository.findById(id).orElse(null);
        return convertDomainToDTO(userInfoById, new UserInfoDTO());
    }

    public UserInfoDTO getUserById(Long id) {
        UserInfo userInfoById = userInfoRepository.findById(id).orElse(null);
        if (userInfoById != null) {
            userInfoById.setUuid(null);
            userInfoById.setPersonId(null);
            return convertDomainToDTO(userInfoById, new UserInfoDTO());
        } else return null;
    }

    public UserInfoDTO createUser(UserInfoDTO userInfoDTO) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoDTO, userInfo);

        UUID uuid = UUID.randomUUID();
        userInfo.setUuid(String.valueOf(uuid));

        validateNewPerson(userInfo.getPersonId());

        BeanUtils.copyProperties(userInfoRepository.save(userInfo), userInfoDTO);
        return userInfoDTO;
    }

    public void validateNewPerson(String personId) {
        if (personId.length() != MAX_LENGTH_PERSON_ID) {
            String errorMessage = "PersonId length doesn't match. It needs to have exactly: " + MAX_LENGTH_PERSON_ID + " characters";
            logger.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        if (userInfoRepository.existsByPersonIdIgnoreCase(personId)) {
            String errorMessage = "PersonId: " + personId + " already exists";
            logger.error(errorMessage);
            throw new RuntimeException(errorMessage);
        }
    }

    public void updateUserById(Long id, UserInfoDTO userInfoDTO) {
        UserInfo convertUserInfo = mapDTOToDomain(userInfoDTO);
        userInfoRepository.findById(id).ifPresent(
                existingUserInfo -> {
                    existingUserInfo.setName(convertUserInfo.getName());
                    existingUserInfo.setSurname(convertUserInfo.getSurname());
                    userInfoRepository.save(existingUserInfo);
                });
    }

    public void delete(Long id) {
        userInfoRepository.deleteById(id);
    }

}
