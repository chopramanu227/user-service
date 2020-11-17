package com.example.user.controller;

import com.example.user.exception.RecordNotFoundException;
import com.example.user.model.Address;
import com.example.user.model.User;
import com.example.user.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(properties = { "management.security.enabled=false, spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration" })
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@AutoConfigureMockMvc
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void shouldHaveMockMvcInitialised() {
        Assertions.assertNotNull(mockMvc);
    }

    @Test
    public void shouldHaveUserServiceInitialised() {
        Assertions.assertNotNull(userService);
    }

    @Test
    public void givenUserPresentInDB_whenGetAllUsers_thenReturnUserJsonObject() throws Exception{
        given(userService.fetchAllUsers()).willReturn(Arrays.asList(mockUser()));
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].firstName", is("fname")));
    }

    @Test
    public void givenNoUserPresentInDB_whenGetAllUsers_thenReturnUserJsonObject() throws Exception{
        given(userService.fetchAllUsers()).willReturn(Collections.emptyList());
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void givenUserPresentInDB_whenGetUser_thenReturnUserJsonObject() throws Exception{
        Long userId = Long.valueOf("1001");
        given(userService.fetchUser(userId)).willReturn(mockUser());
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}",userId)
                .contentType(MediaType.APPLICATION_JSON));
        MvcResult MvcResult=resultActions.andReturn();
        resultActions.andDo(print());

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.firstName", is("fname")));
    }

    @Test
    public void givenUserNotPresentInDB_whenGetUser_thenReturnUserJsonObject() throws Exception{
        Long userId = Long.valueOf("1021");
        given(userService.fetchUser(userId)).willThrow(RecordNotFoundException.class);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}",userId)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andDo(print());
        resultActions.andExpect(MockMvcResultMatchers.status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @Test
    public void givenUserPresentInDB_whenUpdateUser_thenReturnUserJsonObject() throws Exception{
        Long userId = Long.valueOf("1001");
        User newUser=mockUser();
        newUser.setFirstName("nfname");
        Gson gson= new Gson();
        given(userService.updateUser(userId,mockUser())).willReturn(mockUser());
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}",userId)
                .content(gson.toJson(newUser))
                .with(httpBasic("1001","test"))
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andDo(print());
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    }

    private User mockUser() {
        return User.builder()
                .firstName("fname")
                .lastName("lastname")
                .gender("male")
                .title("Mr")
                .address(mockAddress())
                .build();
    }

    private Address mockAddress() {
        return Address.builder()
                .city("Sydney")
                .postcode("2000")
                .state("NSW")
                .street("Test street")
                .build();
    }

}