package com.example.feat006.controller;

import com.example.feat006.common.ApiResponse;
import com.example.feat006.service.DedupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dedup")
@RequiredArgsConstructor
public class DedupController {

    private final DedupService dedupService;

    @GetMapping("/send")
    public ApiResponse<?> send(@RequestParam String messageId, @RequestParam String content) {
        long start = System.currentTimeMillis();
        var data = dedupService.process(messageId, content);
        boolean processed = Boolean.TRUE.equals(data.get("processed"));
        return ApiResponse.of(processed,
                processed ? "메시지 처리 완료" : "중복 메시지 - 무시됨",
                data, System.currentTimeMillis() - start);
    }
}
