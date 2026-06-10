package com.example.feat006.controller;

import com.example.feat006.common.ApiResponse;
import com.example.feat006.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SyncAsyncController {

    private final LoginService loginService;

    @GetMapping("/sync/login")
    public ApiResponse<?> syncLogin(@RequestParam String id) throws InterruptedException {
        long start = System.currentTimeMillis();
        var data = loginService.loginSync(id);
        return ApiResponse.of(true, "동기 로그인 완료 (포인트 지급 완료 후 응답)", data, System.currentTimeMillis() - start);
    }

    @GetMapping("/async/login")
    public ApiResponse<?> asyncLogin(@RequestParam String id) {
        long start = System.currentTimeMillis();
        var data = loginService.loginAsync(id);
        return ApiResponse.of(true, "비동기 로그인 완료 (포인트 지급은 백그라운드)", data, System.currentTimeMillis() - start);
    }
}
