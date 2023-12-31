package com.engeto.genesis.repository;

import com.engeto.genesis.domain.UserInfo;
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
        //GIVEN
        assertThat(userInfoRepository.findAll()).isEmpty();
        String personId = "somePersonId";
        userInfoRepository.save(new UserInfo("Stefan", "Impulse", personId, "someUuid"));
        assertThat(userInfoRepository.findAll()).hasSize(1);

        //WHEN
        boolean exist = userInfoRepository.existsByPersonIdIgnoreCase(personId);

        //THEN
        assertThat(exist).isTrue();
    }

    @Test
    void GIVEN_one_entity_in_DB_WHEN_existsByPersonIdIgnoreCase_with_non_existing_personId_THEN_false() {
        //GIVEN
        assertThat(userInfoRepository.findAll()).isEmpty();
        String personId = "somePersonId";
        userInfoRepository.save(new UserInfo("Stefan", "Impulse", personId, "someUuid"));
        assertThat(userInfoRepository.findAll()).hasSize(1);

        //WHEN
        boolean exist = userInfoRepository.existsByPersonIdIgnoreCase("nonExistPersonId");

        //THEN
        assertThat(exist).isFalse();
    }

    @Test
    void GIVEN_one_entity_in_DB_WHEN_existsByPersonIdIgnoreCase_with_UPPER_CASE_personId_THEN_true() {
        //GIVEN
        assertThat(userInfoRepository.findAll()).isEmpty();
        String personId = "somePersonId";
        userInfoRepository.save(new UserInfo("Stefan", "Impulse", personId, "someUuid"));
        assertThat(userInfoRepository.findAll()).hasSize(1);

        //WHEN
        boolean exist = userInfoRepository.existsByPersonIdIgnoreCase(personId.toUpperCase());

        //THEN
        assertThat(exist).isTrue();
    }

    @Test
    void GIVEN_one_entity_in_DB_WHEN_existsByPersonIdIgnoreCase_with_DOWN_CASE_personId_THEN_true() {
        //GIVEN
        assertThat(userInfoRepository.findAll()).isEmpty();
        String personId = "somePersonId";
        userInfoRepository.save(new UserInfo("Stefan", "Impulse", personId, "someUuid"));
        assertThat(userInfoRepository.findAll()).hasSize(1);

        //WHEN
        boolean exist = userInfoRepository.existsByPersonIdIgnoreCase(personId.toLowerCase());

        //THEN
        assertThat(exist).isTrue();
    }

    @Test
    void GIVEN_tree_entities_in_DB_WHEN_existsByPersonIdIgnoreCase_with_existing_personId_THEN_true() {
        // GIVEN
        assertThat(userInfoRepository.findAll()).isEmpty();
        String personId = "123456989951";
        userInfoRepository.save(new UserInfo("Jack", "Sparow", "123456789654", "someUuid"));
        userInfoRepository.save(new UserInfo("John", "Doe",personId , "someUuid2"));
        userInfoRepository.save(new UserInfo("Alice", "Wonderland", "123654789951", "someUuid3"));
        assertThat(userInfoRepository.findAll()).hasSize(3);

        // WHEN
        boolean exist = userInfoRepository.existsByPersonIdIgnoreCase(personId);

        // THEN
        assertThat(exist).isTrue();
    }

}
