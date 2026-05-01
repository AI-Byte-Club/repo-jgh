package com.vibe.login.service;

import com.vibe.login.domain.Member;
import com.vibe.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(String username, String password, String role) {
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // 기본 권한 설정 (값이 없으면 BASIC)
        String userRole = (role != null && !role.isEmpty()) ? role : "ROLE_USER_BASIC";

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(userRole)
                .build();

        memberRepository.save(member);
    }
}
