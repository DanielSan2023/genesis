package com.engeto.genesis.repository;

import com.engeto.genesis.domain.UserInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.junit.Assert.assertSame;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserInfoRepositoryTest {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Test
    public void setUserInfoRepository_Save_userInfo_Return_saved_UserInfo() {
        UserInfo userInfo = new UserInfo("mike", "wazovsky", "123456789123", "someUuid");

        UserInfo savedUserInfo = userInfoRepository.save(userInfo);

        Assertions.assertThat(savedUserInfo).isNotNull();
        Assertions.assertThat(savedUserInfo.getId()).isGreaterThan(0);
    }
//    @Test
//    public void setUserInfoRepository_SaveAll_than_Return_saved_UserInfos(){
//
//        UserInfo userInfo1 = new UserInfo("mike", "wazovsky", "123456759123", "someUuid");
//        UserInfo userInfo2 = new UserInfo("michael", "jordan", "123456569123", "someUuid");
//
//      userInfoRepository.save(userInfo1);
//      userInfoRepository.save(userInfo2);
//        List<UserInfo> userInfoList = userInfoRepository.findAll();
//
//        Assertions.assertThat(userInfoList).isNotNull();
//        Assertions.assertThat(userInfoList.size()).isEqualTo(2);
//    }//TODO need  it  to checking and repairing

    @Test
    public void UserInfoRepository_FindById_ReturnUserInfo() {
        UserInfo userInfo = new UserInfo("mike", "wazovsky", "123456789123", "someUuid");

        userInfoRepository.save(userInfo);
        UserInfo returnedUserInfoById = userInfoRepository.findById(userInfo.getId()).get();

        Assertions.assertThat(returnedUserInfoById).isNotNull();
        assertSame("Verify that tweo objects are the same", userInfo, returnedUserInfoById);

    }

    @Test
    public void UserInfoRepository_UserInfo_DELETEById_ReturnUserInfo_is_Empty() {
        UserInfo userInfo = new UserInfo("mike", "wazovsky", "123456789123", "someUuid");

        userInfoRepository.save(userInfo);
        userInfoRepository.deleteById(userInfo.getId());
        Optional<UserInfo> returnedUserInfo = userInfoRepository.findById(userInfo.getId());

        Assertions.assertThat(returnedUserInfo).isEmpty();

    }

}
