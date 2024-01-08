package com.engeto.genesis.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserInfoTest {

    @Test
    void GIVEN_userInfo_WHEN_toString_THEN_checked() {
        // GIVEN
        UserInfo userInfo = new UserInfo("John", "Doe", "123456789012", "someUuid");

        // WHEN
        String toStringResult = userInfo.toString();

        // THEN
        String expectedString = "UserInfo(id=null, name=John, surname=Doe, personId=123456789012, uuid=someUuid)";
        assertThat(toStringResult).isEqualTo(expectedString);
    }
}