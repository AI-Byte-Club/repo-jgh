package com.example.feat006.controller;

import com.example.feat006.common.ApiResponse;
import com.example.feat006.entity.Outbox;
import com.example.feat006.service.OutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/outbox")
@RequiredArgsConstructor
public class OutboxController {

    private final OutboxService outboxService;

    @GetMapping("/create")
    public ApiResponse<?> create(@RequestParam String product) {
        long start = System.currentTimeMillis();
        Map<String, Object> data = outboxService.create(product);
        return ApiResponse.of(true, "주문 + 아웃박스 메시지 동일 트랜잭션 저장 완료", data, System.currentTimeMillis() - start);
    }

    @GetMapping("/process")
    public ApiResponse<?> process() {
        long start = System.currentTimeMillis();
        List<Map<String, Object>> results = outboxService.process();
        return ApiResponse.of(true, "아웃박스 메시지 처리 완료",
                Map.of("count", results.size(), "results", results), System.currentTimeMillis() - start);
    }

    @GetMapping("/list")
    public ApiResponse<?> list() {
        long start = System.currentTimeMillis();
        List<Outbox> list = outboxService.list();
        return ApiResponse.of(true, "아웃박스 전체 조회",
                Map.of("total", list.size(), "list", list), System.currentTimeMillis() - start);
    }

    @GetMapping("/fail-test")
    public ApiResponse<?> failTest(@RequestParam String product) {
        long start = System.currentTimeMillis();
        Map<String, Object> data = outboxService.createFailTest(product);
        return ApiResponse.of(true, "실패 테스트 아웃박스 생성 완료 - /outbox/process 3회 호출 후 FAILED 확인", data, System.currentTimeMillis() - start);
    }
}
