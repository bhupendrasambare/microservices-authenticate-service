package com.service.authenticate.controller;

import com.service.authenticate.config.Constants;
import com.service.authenticate.config.UserService;
import com.service.authenticate.dto.request.LoginRequest;
import com.service.authenticate.dto.response.Response;
import com.service.authenticate.dto.Status;
import com.service.authenticate.dto.response.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Controller", description = "APIs for Authentication")
public class authController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Registration", description = "User registration with unique email")
    public ResponseEntity<Response> addUser(@RequestBody UserDto users){
        return userService.createUser(users);
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate user and return a token")
    public ResponseEntity<Response> login(@Valid @RequestBody LoginRequest users) {
        ResponseEntity<Response> res;
        String token = userService.generateToken(users.getEmail(), users.getPassword());
        res = ResponseEntity.status(HttpStatus.OK).body(new Response(Constants.SUCCESS_CODE,"", Status.SUCCESS,token));
        return res;
    }

    @PostMapping("/validate")
    @Operation(summary = "Validate token", description = "Validate your token")
    public ResponseEntity<Response> validateToken(@RequestBody String token) {
        ResponseEntity<Response> res;
        try {
            userService.validateToken(token);
            res = ResponseEntity.status(HttpStatus.OK).body(new Response(Constants.SUCCESS_CODE,"", Status.SUCCESS,null));
        } catch (UsernameNotFoundException e) {
            res = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response(Constants.INVALID_TOKEN_CODE,Constants.INVALID_TOKEN, Status.FAILED,null));
        } catch (Exception e) {
            res = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response(Constants.INVALID_TOKEN_CODE,Constants.INVALID_TOKEN, Status.FAILED,null));
        }
        return res;
    }

}
