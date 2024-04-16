package com.engeto.genesis.repository;

import com.engeto.genesis.domain.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ContactTypeRepository extends JpaRepository<ContactType,Long> {

   boolean existsByType(String type);
}
