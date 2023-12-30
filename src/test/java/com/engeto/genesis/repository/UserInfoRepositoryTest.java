package com.engeto.genesis.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserInfoRepositoryTest {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    void GIVEN_empty_DB_WHEN_existsByPersonIdIgnoreCase_THEN_false() {
        assertThat(userInfoRepository.findAll()).isEmpty();

        boolean exist = userInfoRepository.existsByPersonIdIgnoreCase("somePersonId");

        assertThat(exist).isFalse();
    }

    @Test
    void GIVEN_one_entity_in_DB_WHEN_existsByPersonIdIgnoreCase_with_existing_personId_THEN_true() {
    }

    @Test
    void GIVEN_one_entity_in_DB_WHEN_existsByPersonIdIgnoreCase_with_mon_existing_personId_THEN_false() {
    }

    @Test
    void GIVEN_one_entity_in_DB_WHEN_existsByPersonIdIgnoreCase_with_UPPER_CASE_personId_THEN_true() {
    }

    @Test
    void GIVEN_one_entity_in_DB_WHEN_existsByPersonIdIgnoreCase_with_DOWN_CASE_personId_THEN_true() {
    }

}
