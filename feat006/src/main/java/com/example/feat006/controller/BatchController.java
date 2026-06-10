package com.example.feat006.controller;

import com.example.feat006.common.ApiResponse;
import com.example.feat006.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {

    private final BatchService batchService;

    @GetMapping("/generate")
    public ApiResponse<?> generate(@RequestParam(defaultValue = "10") int count) {
        long start = System.currentTimeMillis();
        int generated = batchService.generate(count);
        return ApiResponse.of(true, generated + "건 배치 데이터 생성 완료 (is_sent=false)",
                java.util.Map.of("generatedCount", generated), System.currentTimeMillis() - start);
    }

    @GetMapping("/send")
    public ApiResponse<?> send() {
        long start = System.currentTimeMillis();
        var result = batchService.send();
        return ApiResponse.of(true, "배치 전송 완료", result, System.currentTimeMillis() - start);
    }

    @GetMapping("/status")
    public ApiResponse<?> status() {
        long start = System.currentTimeMillis();
        var data = batchService.status();
        return ApiResponse.of(true, "배치 전송 현황", data, System.currentTimeMillis() - start);
    }
}
