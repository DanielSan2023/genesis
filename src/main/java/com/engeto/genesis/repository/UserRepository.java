package com.engeto.genesis.repository;

import com.engeto.genesis.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<UserInfo, Long> {

}

