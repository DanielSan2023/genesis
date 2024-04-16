package com.engeto.genesis.controller;

import com.engeto.genesis.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @MockBean
    private UserController userController;

    @MockBean
    private UserInfoService userInfoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void GIVEN_invalid_user_name_When_PostRequest_user_Then_BadRequests() throws Exception {
        String user = "{ \"surname\" : \"\", \"personId\" : \"123\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("name shouldn't be null"))
                .andExpect(jsonPath("$.personId").value("Person ID must be exactly 12 characters long"));
    }

    @Test
    public void GIVEN_invalid_user_name_and_surname_and_personId_When_PostRequest_user_Then_BadRequests() throws Exception {
        String user = "{}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("name shouldn't be null"))
                .andExpect(jsonPath("$.surname").value("surname shouldn't be null"))
                .andExpect(jsonPath("$.personId").value("It needs to have exactly: 12 characters"));
    }

    @Test
    public void GIVEN_valid_user_WHEN_PostRequest_to_User_THEN_CorrectResponse() throws Exception {
        String user = "{ \"name\" : \"Jack\",\"surname\" : \"Smith\", \"personId\" : \"123555777888\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                        .content(user)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
