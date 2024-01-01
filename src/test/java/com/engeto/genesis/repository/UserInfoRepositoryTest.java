package com.engeto.genesis.repository;

import com.engeto.genesis.domain.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserInfoRepositoryTest {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    void GIVEN_empty_DB_WHEN_existsByPersonIdIgnoreCase_THEN_false() {
        //GIVEN
        assertThat(userInfoRepository.findAll()).isEmpty();

        //WHEN
        boolean exist = userInfoRepository.existsByPersonIdIgnoreCase("somePersonId");

        //THEN
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
        userInfoRepository.save(new UserInfo("John", "Doe", personId, "someUuid2"));
        userInfoRepository.save(new UserInfo("Alice", "Wonderland", "123654789951", "someUuid3"));
        assertThat(userInfoRepository.findAll()).hasSize(3);

        // WHEN
        boolean exist = userInfoRepository.existsByPersonIdIgnoreCase(personId);

        // THEN
        assertThat(exist).isTrue();
    }

    @Test
    void GIVEN_userInfo_entity_to_DB_WHEN_save_user_THEN_return_saved_user() {
        //GIVEN
        UserInfo newUser = new UserInfo("Jack", "Johnson", "321654987951", "someUuid");
        userInfoRepository.save(newUser);

        // WHEN
        UserInfo savedUser = userInfoRepository.save(newUser);

        // THEN
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    void GIVEN_userInfoList_WHEN_findAll_users_THEN_return_userInfoList() {
        //GIVEN
        userInfoRepository.save(new UserInfo("Jack", "Sparow", "123456789654", "someUuid"));
        userInfoRepository.save(new UserInfo("John", "Doe", "123654789951", "someUuid2"));

        //WHEN
        List<UserInfo> usersList = userInfoRepository.findAll();

        //THEN
        assertThat(usersList).isNotNull();
        assertThat(usersList.size()).isEqualTo(2);
    }

    @Test
    void GIVEN_userInfo_object_WHEN_update_userInfo_THEN_return_updated_userInfo() {
        //GIVEN
        UserInfo newUser = new UserInfo("Jack", "Johnson", "321654987951", "someUuid");
        userInfoRepository.save(newUser);

        //WHEN
        UserInfo savedUser = userInfoRepository.findById(newUser.getId()).get();
        savedUser.setName("Richie");
        savedUser.setSurname("Rich");
        UserInfo updatedUser = userInfoRepository.save(savedUser);

        //THEN
        assertThat(updatedUser.getName()).isEqualTo("Richie");
        assertThat(updatedUser.getSurname()).isEqualTo("Rich");
    }

    @Test
    void GIVEN_userInfo_object_WHEN_delete_userInfo_THEN_remove_userInfo() {
        //GIVEN
        UserInfo newUser = new UserInfo("Jack", "Johnson", "321654987951", "someUuid");
        userInfoRepository.save(newUser);

        //WHEN
        userInfoRepository.deleteById(newUser.getId());
        Optional<UserInfo> userInfoOptional = userInfoRepository.findById(newUser.getId());

        //THEN
        assertThat(userInfoOptional).isEmpty();
    }
}
