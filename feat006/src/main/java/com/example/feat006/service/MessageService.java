package com.example.feat006.service;

import com.example.feat006.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final ApplicationEventPublisher eventPublisher;
    private final PointService pointService;

    public Map<String, Object> publishEvent(String type, String orderId) {
        eventPublisher.publishEvent(new OrderPlacedEvent(type, orderId));
        log.info("[Event] 이벤트 발행 - type: {}, orderId: {}", type, orderId);
        return Map.of("type", type, "orderId", orderId, "published", true,
                "description", "구독 중인 핸들러가 자동으로 처리 (콘솔 로그 확인)");
    }

    public Map<String, Object> sendCommand(String type, String userId, int point) {
        log.info("[Command] 커맨드 수신 - type: {}, 수신자: PointService", type);
        Map<String, Object> result = pointService.givePoint(userId, point);
        return Map.of("commandType", type, "target", "PointService", "result", result);
    }
}
