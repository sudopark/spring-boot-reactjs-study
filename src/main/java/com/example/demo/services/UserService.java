package com.example.demo.services;


import com.example.demo.persistence.UserRepository;
import com.example.demo.persistence.entities.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity entity) {
        if(entity == null || entity.getEmail() == null) {
            throw new RuntimeException("Invalid argument");
        }

        final String email = entity.getEmail();
        if(userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(entity);
    }

    public UserEntity getByCredential(final String email, final String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
