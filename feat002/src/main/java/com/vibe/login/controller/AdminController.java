package com.vibe.login.controller;

import com.vibe.login.domain.Member;
import com.vibe.login.repository.MemberRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberRepository memberRepository;

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> getAllMembers() {
        List<MemberResponse> members = memberRepository.findAll().stream()
                .map(member -> new MemberResponse(member.getId(), member.getUsername(), member.getRole()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(members);
    }

    @Data
    @RequiredArgsConstructor
    public static class MemberResponse {
        private final Long id;
        private final String username;
        private final String role;
    }
}
