package com.example.feat006.controller;

import com.example.feat006.common.ApiResponse;
import com.example.feat006.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/thread")
@RequiredArgsConstructor
public class ThreadController {

    private final ThreadService threadService;

    @GetMapping("/new")
    public ApiResponse<?> newThread(@RequestParam String message) {
        long start = System.currentTimeMillis();
        threadService.runWithNewThread(message);
        return ApiResponse.of(true, "new Thread() 실행 완료 - 3초 후 콘솔 로그 확인",
                Map.of("message", message, "method", "new Thread()"), System.currentTimeMillis() - start);
    }

    @GetMapping("/pool")
    public ApiResponse<?> poolThread(@RequestParam String message) {
        long start = System.currentTimeMillis();
        threadService.runWithPool(message);
        return ApiResponse.of(true, "ExecutorService 스레드풀 실행 완료",
                Map.of("message", message, "method", "ExecutorService(50)"), System.currentTimeMillis() - start);
    }

    @GetMapping("/async")
    public ApiResponse<?> asyncThread(@RequestParam String message) {
        long start = System.currentTimeMillis();
        threadService.runWithAsync(message);
        return ApiResponse.of(true, "@Async 비동기 실행 완료",
                Map.of("message", message, "method", "@Async"), System.currentTimeMillis() - start);
    }
}
