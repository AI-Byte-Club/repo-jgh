package com.example.feat006.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class ThreadService {

    private final ExecutorService threadPool = Executors.newFixedThreadPool(50);

    public void runWithNewThread(String message) {
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                log.info("[New Thread] 3초 후 백그라운드 작업 완료 - message: {}", message);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void runWithPool(String message) {
        threadPool.submit(() -> log.info("[Thread Pool] 스레드풀 작업 완료 - message: {}", message));
    }

    @Async
    public void runWithAsync(String message) {
        log.info("[Async] @Async 비동기 작업 처리 - message: {}", message);
    }
}
