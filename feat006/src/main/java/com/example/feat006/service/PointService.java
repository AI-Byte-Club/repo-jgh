package com.example.feat006.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class PointService {

    public void givePointSync(String userId) throws InterruptedException {
        Thread.sleep(2000);
        log.info("[Sync] 포인트 지급 완료 - userId: {}", userId);
    }

    @Async
    public void givePointAsync(String userId) {
        try {
            Thread.sleep(2000);
            log.info("[Async] 포인트 지급 완료 (백그라운드) - userId: {}", userId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Map<String, Object> givePoint(String userId, int point) {
        log.info("[Command] 포인트 지급 처리 - userId: {}, point: {}", userId, point);
        return Map.of("userId", userId, "point", point, "result", "지급완료");
    }
}
