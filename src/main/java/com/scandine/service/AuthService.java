package com.scandine.service;

import com.scandine.dto.request.LoginRequest;
import com.scandine.dto.response.LoginResponse;
import com.scandine.entity.Staff;
import com.scandine.exception.UnauthorizedException;
import com.scandine.repository.StaffRepository;
import com.scandine.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final StaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        Staff staff = staffRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), staff.getPassword())) {
            throw new UnauthorizedException(
                    "Invalid username or password"
            );
        }
        String token = jwtUtil.generateToken(staff.getUsername(),staff.getRole().name());

        return new LoginResponse(token,staff.getUsername(),staff.getRole().name());
    }
}
