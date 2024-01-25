package com.engeto.genesis.repository;

import com.engeto.genesis.domain.ContactDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ContactDetailRepository extends JpaRepository<ContactDetail,Long> {

   // boolean existsByContactDetailIgnoreCase(String id);
}
