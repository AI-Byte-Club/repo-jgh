package com.example.feat006.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {

    private final PointService pointService;

    public Map<String, Object> loginSync(String userId) throws InterruptedException {
        log.info("[Sync] 로그인 시작 - userId: {}", userId);
        pointService.givePointSync(userId);
        log.info("[Sync] 로그인 내역 저장 - userId: {}", userId);
        return Map.of("userId", userId, "method", "sync", "note", "포인트 지급 완료 후 응답");
    }

    public Map<String, Object> loginAsync(String userId) {
        log.info("[Async] 로그인 시작 - userId: {}", userId);
        pointService.givePointAsync(userId);
        log.info("[Async] 즉시 응답 - userId: {}", userId);
        return Map.of("userId", userId, "method", "async", "note", "포인트 지급은 백그라운드 진행 중");
    }
}
