package com.crud.springbootcrud.controller;

import com.crud.springbootcrud.model.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@PropertySource("classpath:application-test.yml")
@ActiveProfiles("test")
@Testcontainers
@Sql(value = {"/user_data_before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/user_data_after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    @DisplayName("Save user success")
//    void saveUserSuccess() throws Exception {
//        UserDto userDto = UserDto.builder()
//                .firstName("FirstUserName")
//                .lastName("FirstUserLast")
//                .avatar("http://test-avatar-first-user.com")
//                .company("Test Company First User")
//                .email("first@email.com")
//                .jobTitle("Test Job Title First User")
//                .gender("Male")
//                .build();
//
//        mockMvc.perform(post("/api/user")
//                .content(asJsonString(userDto))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").exists())
//                .andExpect(jsonPath("$.email").value("first@email.com"))
//                .andReturn();
//    }

//    @Test
//    @DisplayName("Save user without email")
//    void saveUserWithoutEmail() throws Exception {
//        UserDto userDto = UserDto.builder()
//                .firstName("FirstUserName")
//                .lastName("FirstUserLast")
//                .avatar("http://test-avatar-first-user.com")
//                .company("Test Company First User")
//                .email(null)
//                .jobTitle("Test Job Title First User")
//                .gender("Male")
//                .build();
//
//        mockMvc.perform(post("/api/user")
//                .content(asJsonString(userDto))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$").exists())
//                .andExpect(jsonPath("$.errors[0]").value("email must be present"));
//    }

    @Test
    @DisplayName("Get all users by page 1 and size 2")
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/api/user?page=1&size=2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @DisplayName("Get users by non existent page")
    void getAllUsersByNonExistentPage() throws Exception {
        mockMvc.perform(get("/api/user?page=-1&size=2")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]")
                        .value("page and size must not be less than zero"));
    }

    @Test
    @DisplayName("Get user by id")
    void getUserById() throws Exception {
        mockMvc.perform(get("/api/user/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("admin@email.com"));
    }

    @Test
    @DisplayName("Get user by id not found")
    void getUserByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/user/{id}", 23L)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.errors").value("User with id: 23 not found"));
    }

    @Test
    @DisplayName("User updated success")
    void updateUser() throws Exception {
        UserDto userDto = UserDto.builder()
                .id(1L)
                .firstName("UpdatedUserName")
                .lastName("UpdatedUserLast")
                .avatar("http://test-avatar-Updated-user.com")
                .company("Test Company Updated User")
                .email("Updated@email.com")
                .jobTitle("Test Job Title Updated User")
                .gender("Male")
                .build();

        mockMvc.perform(put("/api/user")
                .content(asJsonString(userDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("UpdatedUserName"));
    }

    @Test
    @DisplayName("User deleted success")
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/api/user/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successful"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}