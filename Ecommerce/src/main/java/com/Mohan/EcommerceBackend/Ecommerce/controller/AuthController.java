package com.Mohan.EcommerceBackend.Ecommerce.controller;


import com.Mohan.EcommerceBackend.Ecommerce.Payload.UserDTO;
import com.Mohan.EcommerceBackend.Ecommerce.model.LoginCredentials;
import com.Mohan.EcommerceBackend.Ecommerce.model.User;
import com.Mohan.EcommerceBackend.Ecommerce.service.JWTService;
import com.Mohan.EcommerceBackend.Ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<Map<String,Object>> registerHandler(@Valid @RequestBody User user){

        UserDTO userDTO = userService.registerUser(user);
        String token = jwtService.generateToken(userDTO.getEmail());
        return new ResponseEntity<Map<String,Object>>(Collections.singletonMap("jwt-token",token), HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,Object>> loginHandler(@Valid @RequestBody LoginCredentials credentials){
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(credentials.getEmail(),credentials.getPassword());

        Authentication auth = authenticationManager.authenticate(authToken);

        String token = null;

        if(auth.isAuthenticated()){
            token = jwtService.generateToken(credentials.getEmail());
        }

        return new ResponseEntity<Map<String,Object>>(Collections.singletonMap("jwt-token",token),HttpStatus.OK);


    }

}
