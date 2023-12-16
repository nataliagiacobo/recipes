package com.ada.recipes.service;

import com.ada.recipes.controller.dto.UserRequest;
import com.ada.recipes.controller.dto.UserResponse;
import com.ada.recipes.controller.exception.PasswordValidationExeption;
import com.ada.recipes.model.User;
import com.ada.recipes.repository.UserRepository;
import com.ada.recipes.utils.UserConvert;
import com.ada.recipes.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Page<UserResponse> getUsers(int page, int size, String direction){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), "name");
        Page<User> users = userRepository.findAll(pageRequest);
        return UserConvert.toResponsePage(users);
    }

    public UserResponse saveUser(UserRequest userDTO) throws Exception {
        User user = UserConvert.toEntity(userDTO);
        if (!Validator.passwordValidate(userDTO.getPassword()))
            throw new PasswordValidationExeption("Senha não preenche requisitos");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        User userEntity = userRepository.save(user);
        return UserConvert.toResponse(userEntity);
    }

    public UserResponse getUserById(Integer id){
        return UserConvert.toResponse(userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado")));
    }

    public List<UserResponse> getAllByName(String name){
        return UserConvert.toResponseList(userRepository.findAllByName(name));
    }

    public void deleteUser(Integer id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        user.setActive(false);
        userRepository.save(user);
    }

    public UserResponse updateUser (Integer id, UserRequest userRequest){
        User user = UserConvert.toEntity(userRequest);
        user.setId(id);
        user.setActive(true);
        return UserConvert.toResponse(userRepository.save(user));
    }
}