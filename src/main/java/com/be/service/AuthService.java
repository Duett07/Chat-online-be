package com.be.service;

import com.be.dto.RegisterDTO;
import com.be.entity.User;
import com.be.payload.RegisterResponse;
import com.be.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterDTO register) {

        boolean userCheck = userRepository.existsByUsername(register.getUsername());

        if (userCheck) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(register.getUsername());
        user.setPassword(passwordEncoder.encode(register.getPassword()));

        this.userRepository.save(user);
        return new RegisterResponse(user.getId(), user.getUsername(), user.getCreatedAt());
    }
}
