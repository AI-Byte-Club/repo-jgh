package com.example.feat006.controller;

import com.example.feat006.common.ApiResponse;
import com.example.feat006.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/event")
    public ApiResponse<?> event(@RequestParam String type, @RequestParam String orderId) {
        long start = System.currentTimeMillis();
        var data = messageService.publishEvent(type, orderId);
        return ApiResponse.of(true, "이벤트 발행 - 구독자(@EventListener)가 자동 처리", data, System.currentTimeMillis() - start);
    }

    @GetMapping("/command")
    public ApiResponse<?> command(@RequestParam String type, @RequestParam String userId, @RequestParam int point) {
        long start = System.currentTimeMillis();
        var data = messageService.sendCommand(type, userId, point);
        return ApiResponse.of(true, "커맨드 처리 - PointService 직접 호출", data, System.currentTimeMillis() - start);
    }
}
