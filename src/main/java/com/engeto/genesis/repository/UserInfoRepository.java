package com.engeto.genesis.repository;

import com.engeto.genesis.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    boolean existsByPersonIdIgnoreCase(String personId);
}
