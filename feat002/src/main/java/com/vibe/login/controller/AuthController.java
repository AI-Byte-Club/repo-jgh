package com.vibe.login.controller;

import com.vibe.login.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vibe.login.config.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vibe.login.repository.MemberRepository;
import com.vibe.login.domain.Member;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        try {
            memberService.register(request.getUsername(), request.getPassword(), request.getRole());
            return ResponseEntity.ok("Registration successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Member member = memberRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 ID 입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String token = jwtTokenProvider.createToken(member.getUsername());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("username", member.getUsername());
        response.put("role", member.getRole());

        return ResponseEntity.ok(response);
    }

    @Data
    public static class RegisterRequest {
        private String username;
        private String password;
        private String role;
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
}
