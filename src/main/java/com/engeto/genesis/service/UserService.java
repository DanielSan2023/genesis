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

    public User get(Long id){
        return userRepository.getUserById(id);
    }

    public void createUser(User user){


        // Validace personID a ověření, zda uživatel již neexistuje v databázi
        if (!isValidPersonID(user.getPersonId()) || userRepository.existsByPersonID(user.getPersonId())) {
            throw new IllegalArgumentException("Neplatné nebo duplicitní personID.");
        }

        // Vygenerování UUID
        String uuid = UUID.randomUUID().toString();

        // Vytvoření instance User
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setSurname(user.getSurname());
        newUser.setPersonId(user.getPersonId());
        newUser.setUuid(uuid);

        // Uložení uživatele do databáze
        userRepository.createUser(newUser);
    }

    private boolean isValidPersonID(String personID) {
        // Zde provedete ověření personID podle vašich požadavků
        // Například můžete ověřit délku, formát, apod.
        return personID != null && personID.length() == 12;
    }


}
