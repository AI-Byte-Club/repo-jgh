package com.example.feat006.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderEventHandler {

    @EventListener
    public void handleOrderPlaced(OrderPlacedEvent event) {
        log.info("[Event Handler] 이벤트 수신 - type: {}, orderId: {}", event.getType(), event.getOrderId());
    }
}
