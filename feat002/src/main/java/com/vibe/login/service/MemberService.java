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

    public void register(String username, String password) {
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        Member member = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role("ROLE_USER")
                .build();

        memberRepository.save(member);
    }
}
