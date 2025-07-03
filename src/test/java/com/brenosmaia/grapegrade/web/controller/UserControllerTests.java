package com.brenosmaia.grapegrade.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.brenosmaia.grapegrade.utils.TestHelper;
import com.brenosmaia.grapegrade.entity.User;
import com.brenosmaia.grapegrade.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@TestPropertySource(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration",
    "spring.jpa.hibernate.ddl-auto=none",
    "spring.data.mongodb.auto-index-creation=false"
})
public class UserControllerTests {

    @MockBean
    UserService userService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    User existingUser, newUser, updateUser;

    @BeforeEach
    public void setUp() {
        newUser = TestHelper.buildUserWithId();
        existingUser = TestHelper.buildUserWithId();
        updateUser = TestHelper.buildUserWithId();
    }

    @Test
    public void should_get_all_users() throws Exception {
        given(userService.getAllUsers()).willReturn(Arrays.asList(existingUser, updateUser));

        this.mockMvc
                .perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void should_get_user_by_id() throws Exception {
        given(userService.getUserById(existingUser.getId())).willReturn(existingUser);

        this.mockMvc
                .perform(get("/users/"+existingUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingUser.getId())))
                .andExpect(jsonPath("$.name", is(existingUser.getName())))
                .andExpect(jsonPath("$.email", is(existingUser.getEmail())));
    }

    @Test
    public void should_create_user() throws Exception {
        given(userService.createUser(org.mockito.ArgumentMatchers.any(User.class))).willReturn(newUser);

        String userJson = objectMapper.writeValueAsString(newUser);
        System.out.println("User JSON: " + userJson);

        this.mockMvc
                .perform(post("/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(newUser.getName())))
                .andExpect(jsonPath("$.email", is(newUser.getEmail())));
    }

    @Test
    public void should_update_user() throws Exception {
        given(userService.updateUser(org.mockito.ArgumentMatchers.any(User.class))).willReturn(existingUser);

        String userJson = objectMapper.writeValueAsString(existingUser);
        System.out.println("Update User JSON: " + userJson);

        this.mockMvc
                .perform(put("/users/"+existingUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(existingUser.getId())))
                .andExpect(jsonPath("$.name", is(existingUser.getName())))
                .andExpect(jsonPath("$.email", is( existingUser.getEmail())));
    }

    @Test
    public void should_delete_user() throws Exception {
        doNothing().when(userService).deleteUser(existingUser.getId());

        this.mockMvc
                .perform(delete("/users/"+existingUser.getId()))
                .andExpect(status().isOk());
    }
}
