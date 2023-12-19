package com.engeto.genesis.service;

import com.engeto.genesis.model.User;
import com.engeto.genesis.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.getAll();
    }
}
