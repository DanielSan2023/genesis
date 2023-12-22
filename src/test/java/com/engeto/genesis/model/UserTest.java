package com.engeto.genesis.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;


class UserTest {

    UserInfo user;

    @BeforeEach
    void setUp() {
        user = new UserInfo(
                "Jack",
                "Sperow",
                "25461",
                "550e8400-e29b-41d4-a716-446655440000");
    }

    @Test
    void getName() {
        String actual = user.getName();
        String expect = "Jack";
        Assertions.assertEquals(actual, expect);
    }

    @Test
    void setName() {
        user.setName("Alan");
        String actual = user.getName();
        String expect = "Alan";
        Assertions.assertEquals(actual, expect);
    }

    @Test
    void getSurname() {
        String actual = user.getSurname();
        String expect = "Sperow";
        Assertions.assertEquals(actual, expect);
    }

    @Test
    void setSurname() {
        user.setSurname("Defoe");
        String actual = user.getSurname();
        String expect = "Defoe";
        Assertions.assertEquals(actual, expect);
    }

    @Test
    void getPersonId() {
        String actual = user.getPersonId();
        String expect = "25461";
        Assertions.assertEquals(actual, expect);
    }

    @Test
    void setPersonId() {
        user.setPersonId("254151");
        String actual = user.getPersonId();
        String expect = "254151";
        Assertions.assertEquals(actual, expect);
    }

    @Test
    @DisplayName("Test compare uuid")
    void getUuid() {
        UUID actual = UUID.fromString(user.getUuid());
        UUID expect = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        Assertions.assertEquals(expect, actual);
    }

    @Test
    void setUuid() {
        user.setUuid("550e8400-e29b-41d4-a716-446655480000");
        UUID actual = UUID.fromString(user.getUuid());
        UUID expect = UUID.fromString("550e8400-e29b-41d4-a716-446655480000");
        Assertions.assertEquals(expect, actual);
    }
}