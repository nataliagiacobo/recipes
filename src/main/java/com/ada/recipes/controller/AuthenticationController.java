package com.ada.recipes.controller;

import com.ada.recipes.controller.dto.LoginRequest;
import com.ada.recipes.controller.dto.TokenResponse;
import com.ada.recipes.infra.security.TokenService;
import com.ada.recipes.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    @Autowired
    TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping
    public ResponseEntity login(@RequestBody @Valid LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken autheticate = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        var authentication = authenticationManager.authenticate(autheticate);
        var token = tokenService.tokenGenerate((User) authentication.getPrincipal());

        return ResponseEntity.ok().body(new TokenResponse(token));
    }
}
