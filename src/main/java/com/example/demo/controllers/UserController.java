package com.example.demo.controllers;

import com.example.demo.models.ResponseDTO;
import com.example.demo.models.UserDTO;
import com.example.demo.persistence.entities.UserEntity;
import com.example.demo.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {

        try {

            UserEntity entity = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .username(userDTO.getUsername())
                    .id(userDTO.getId())
                    .password(userDTO.getPassword())
                    .build();

            UserEntity registeredUser = this.userService.create(entity);

            UserDTO savedUserDTO = UserDTO.builder()
                    .email(registeredUser.getEmail())
                    .id(registeredUser.getId())
                    .username(registeredUser.getUsername())
                    .build();

            return ResponseEntity.ok().body(savedUserDTO);

        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {

        UserEntity user = this.userService.getByCredential(userDTO.getEmail(), userDTO.getPassword());

        if(user != null) {
            final UserDTO findUserDTo = UserDTO.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .build();
            return ResponseEntity.ok().body(findUserDTo);
        } else {
            ResponseDTO errorResponse = ResponseDTO.builder().error("login failed").build();
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
}
