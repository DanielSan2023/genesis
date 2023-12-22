package com.engeto.genesis.service;

import com.engeto.genesis.model.UserInfo;
import com.engeto.genesis.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserInfo> getUsers() {
        return userRepository.findAll();
    }

    public List<UserInfo> getUsersDetail() {
        return userRepository.findAll();
    }

    public UserInfo getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserInfo createUser(UserInfo userInfo) {
        return userRepository.save(userInfo);
    }

    private boolean isValidPersonID(String personID) {
        return personID != null && personID.length() == 12;
    }

    public void updateUserById(Long id, UserInfo userInfo) {
//        userRepository.updateUserById(id, userInfo);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
