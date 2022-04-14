package com.example.demo.services;


import com.example.demo.persistence.UserRepository;
import com.example.demo.persistence.entities.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity entity, final PasswordEncoder passwordEncoder) {
        if(entity == null || entity.getEmail() == null || entity.getPassword() == null) {
            throw new RuntimeException("Invalid argument");
        }

        // password 저장할때 encode 해줘야하는거아님??

        final String email = entity.getEmail();
        if(userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(entity.getPassword());
        entity.setPassword(encodedPassword);

        return userRepository.save(entity);
    }

    public UserEntity getByCredential(
            final String email,
            final String password,
            final PasswordEncoder passwordEncoder
            ) {
        final UserEntity originalUser = userRepository.findByEmail(email);
        if(originalUser != null && passwordEncoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }
        return null;
    }
}
