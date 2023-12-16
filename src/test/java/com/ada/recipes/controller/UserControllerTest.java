package com.ada.recipes.controller;

import com.ada.recipes.controller.dto.UserResponse;
import com.ada.recipes.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    public void should_register_user() throws Exception {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(100);
        userResponse.setName("Maria");
        userResponse.setEmail("maria@email.com");

        when(userService.saveUser(any())).thenReturn(userResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content("""
                                    {
                                        "name": "Maria",
                                        "email": "maria@email.com",
                                        "password": "Senha@12345"
                                    }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists()
        );
    }
    @Test
    public void should_not_be_able_to_register_user_without_name() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                        .content("""
                                    {
                                        "email": "maria@email.com",
                                        "password": "Senha@12345"
                                    }
                                """)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    public void should_list_all_existing_users() throws Exception {
        int page = 0;
        int size = 10;
        String direction = "ASC";
        UserResponse user = new UserResponse();
        user.setId(100);
        user.setName("Natalia");
        user.setEmail("natalia@email.com");

        Page<UserResponse> userResponsePage = new PageImpl<>(Collections.singletonList(user));

        when(userService.getUsers(page, size, direction)).thenReturn(userResponsePage);

        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .param("direction", direction)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("content[0].name").value("Natalia")
        );
    }

    @Test
    public void should_get_user_by_id() throws Exception {
        int id = 100;

        UserResponse user = new UserResponse();
        user.setId(100);
        user.setName("Natalia");
        user.setEmail("natalia@email.com");

        when(userService.getUserById(id)).thenReturn(user);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/" + id)
                        .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("name").value("Natalia")
        );
    }

    @Test
    public void should_update_user() throws Exception {
        int id = 100;

        //UserRequest userRequest = new UserRequest("Maria", "maria@email.com", "Senha@12345");

        UserResponse userResponse = new UserResponse();
        userResponse.setId(100);
        userResponse.setName("Maria");
        userResponse.setEmail("maria@email.com");

        when(userService.updateUser(eq(id), any())).thenReturn(userResponse);

        mockMvc.perform(
            MockMvcRequestBuilders.put("/user/" + id)
                .content("""
                            {
                                "name": "Maria",
                                "email": "maria@email.com",
                                "password": "Senha@12345"
                            }
                        """)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("name").value("Maria")
        );
    }

    @Test
    public void should_delete_user() throws Exception {
        int id = 100;

        doNothing().when(userService).deleteUser(id);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/user/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void should_get_all_user_by_name() throws Exception {
        String name = "Natalia";

        UserResponse user = new UserResponse();
        user.setId(100);
        user.setName(name);
        user.setEmail("natalia@email.com");

        List<UserResponse> userResponsePage = Collections.singletonList(user);

        when(userService.getAllByName(name)).thenReturn(userResponsePage);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/user/name/" + name)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk()
        );
    }
}