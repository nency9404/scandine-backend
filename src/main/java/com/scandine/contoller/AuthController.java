package com.scandine.contoller;

import com.scandine.dto.request.LoginRequest;
import com.scandine.dto.response.LoginResponse;
import com.scandine.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth APIs", description = "Authentication endpoints")
public class AuthController {
      private final AuthService authService;

      @PostMapping("/login")
      @Operation(summary = "Staff login — returns JWT token")
      public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
          return ResponseEntity.ok(authService.login(request));
      }
}
