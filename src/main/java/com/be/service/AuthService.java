package com.be.service;

import com.be.dto.AuthDTO;
import com.be.entity.User;
import com.be.payload.AccountResponse;
import com.be.payload.AuthResponse;
import com.be.payload.LoginResponse;
import com.be.repository.IUserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwt;

    @Value("${access.token}")
    private long expAccessToken;

    @Value("${refresh.token}")
    private long expRefreshToken;

    public AuthResponse register(AuthDTO register) {

        boolean userCheck = userRepository.existsByUsername(register.getUsername());

        if (userCheck) {
            throw new IllegalArgumentException("Tên người dùng đã tồn tại");
        }

        User user = new User();
        user.setUsername(register.getUsername());
        user.setPassword(passwordEncoder.encode(register.getPassword()));

        this.userRepository.save(user);
        return new AuthResponse(user.getId(), user.getUsername(), user.getCreatedAt());
    }

    public LoginResponse login(AuthDTO login, HttpServletResponse response) {
        User user = userRepository.findUserByUsername(login.getUsername());

        if (user == null) {
            throw new IllegalArgumentException("Tài khoản không tồn tại. Vui lòng đăng ký để tiếp tục đăng nhập.");
        }

        if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Tên người dùng hoặc mật khẩu không đúng");
        }

        Map<String, Object> claims = new HashMap<>();
        String accessToken = jwt.generateToken(claims, user);
        String refreshToken = jwt.generateRefreshToken(user);
        long exAccessToken = System.currentTimeMillis() + expAccessToken;
        String expiresAt = Instant.ofEpochMilli(exAccessToken).toString();

        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(expRefreshToken)
                .sameSite("Strict")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        AccountResponse account = new AccountResponse(user.getId(), user.getUsername());

        return new LoginResponse(account,  accessToken, expiresAt);
    }
}
