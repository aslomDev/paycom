package com.uz.paycom.controller;


import com.uz.paycom.ServiceBean.UserService;
import com.uz.paycom.entity.Users;
import com.uz.paycom.payload.ApiResponse;
import com.uz.paycom.payload.JwtResponse;
import com.uz.paycom.security.AuthService;
import com.uz.paycom.security.JwtTokenProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticate;
    private final AuthService authService;
    private final UserService userService;

    public UserController(JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticate, AuthService authService, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticate = authenticate;
        this.authService = authService;
        this.userService = userService;
    }


    @CrossOrigin(origins = "paycom-2.herokuapp.com")
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody Users user) throws InterruptedException {
        ApiResponse response = authService.register(user);
        if (response.isResult()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("active");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response.getMessage());

    }

    @CrossOrigin(origins = "paycom-2.herokuapp.com")
    @PostMapping("/active")
    public ResponseEntity<?> ActivateUser(@Valid @RequestBody Users user){
        ApiResponse response = authService.activateUser(user);
        if (response.isResult()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(getApiToken(user.getPhoneNumber(), user.getPassword()));
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response.getMessage());

    }

    @CrossOrigin(origins = "paycom-2.herokuapp.com")
    @PostMapping("/login")
    public HttpEntity<?> login(@Valid @RequestBody Users user){
        boolean login = userService.login(user);
        if (login){
            HttpEntity<?> response = getApiToken(user.getPhoneNumber(), user.getPassword());
            if (response.getBody() != null){
                return ResponseEntity.ok(getApiToken(user.getPhoneNumber(), user.getPassword()));
            }
        }
        return  ResponseEntity.ok("xato");

    }

    @CrossOrigin(origins = "paycom-2.herokuapp.com")
    @GetMapping("/users")
    public HttpEntity<?> getAllUser(){
        ApiResponse response = userService.getAllUser();

        return ResponseEntity.ok(response);
    }


    private HttpEntity<?> getApiToken(String login, String password) {
        Authentication authentication = authenticate.authenticate(
                new UsernamePasswordAuthenticationToken(login, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }


}
