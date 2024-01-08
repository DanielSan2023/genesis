package com.engeto.genesis.model;

import com.engeto.genesis.domain.UserInfo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserInfoDTOTest {

    @Test
    void GIVEN_userInfoDTO_WHEN_toString_THEN_checked() {
        // GIVEN
        UserInfoDTO dto = new UserInfoDTO("John", "Doe", "123456789012", "someUuid");

        // WHEN
        String toStringResult = dto.toString();

        // THEN
        String expectedString = "UserInfoDTO(id=null, name=John, surname=Doe, personId=123456789012, uuid=someUuid)";
        assertThat(toStringResult).isEqualTo(expectedString);
    }

}