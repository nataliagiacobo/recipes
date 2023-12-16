package com.ada.recipes.service;

import com.ada.recipes.controller.dto.UserRequest;
import com.ada.recipes.controller.dto.UserResponse;
import com.ada.recipes.model.User;
import com.ada.recipes.repository.UserRepository;
import com.ada.recipes.utils.UserConvert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository repository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void valid_user_should_register_with_sucess() throws Exception {
        User user = new User();
        user.setName("Nome");
        user.setEmail("email@email.com");
        user.setPassword("Senha@123");

        UserRequest userRequest = new UserRequest(user.getName(), user.getEmail(), user.getPassword());

        when(repository.save(any())).thenReturn(user);

        userService.saveUser(userRequest);
    }

    @Test
    public void new_user_should_have_active_status_as_true() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("Nome");
        user.setEmail("email@email.com");
        user.setPassword("Senha@123");

        when(repository.save(any())).thenReturn(user);

        UserRequest userRequest = new UserRequest(user.getName(), user.getEmail(), user.getPassword());

        userService.saveUser(userRequest);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser);
        assertTrue(capturedUser.getActive());
    }

    @Test
    public void search_existing_user_should_return_user() {
        User user = new User();
        user.setId(1);
        user.setName("Nome");
        user.setEmail("email@email.com");
        user.setPassword("Senha@123");

        when(repository.findById(1)).thenReturn(Optional.of(user));

        UserResponse response = userService.getUserById(1);
        assertNotNull(response);
    }
    @Test
    public void search_non_existing_user_should_return_error() {
        Assertions.assertThrows(NoSuchElementException.class,
                () -> userService.getUserById(1)
        );
    }

    @Test
    public void deleted_user_should_have_active_status_as_false() {
        User user = new User();
        user.setId(1);
        user.setName("Nome");
        user.setEmail("email@email.com");
        user.setPassword("Senha@123");

        when(repository.findById(1)).thenReturn(Optional.of(user));

        userService.deleteUser(1);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();

        assertNotNull(capturedUser);
        assertFalse(capturedUser.getActive());
    }


}