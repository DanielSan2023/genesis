package com.engeto.genesis.service;

import com.engeto.genesis.model.User;
import com.engeto.genesis.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserById(Long id) {
        return userRepository.getUserById(id);
    }


    public User createUser(User user) {
        if (!isValidPersonID(user.getPersonId()) || userRepository.existsByPersonID(user.getPersonId())) {
            throw new IllegalArgumentException("Neplatné nebo duplicitní personID.");
        }
        String uuid = UUID.randomUUID().toString();
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setSurname(user.getSurname());
        newUser.setPersonId(user.getPersonId());
        newUser.setUuid(uuid);

        return userRepository.createUser(newUser);
    }


    private boolean isValidPersonID(String personID) {
        return personID != null && personID.length() == 12;
    }

    public void delete(Long id) {
        userRepository.deleteUser(id);
    }

}
