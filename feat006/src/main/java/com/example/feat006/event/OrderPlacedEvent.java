package com.example.feat006.event;

import lombok.Getter;

@Getter
public class OrderPlacedEvent {
    private final String type;
    private final String orderId;

    public OrderPlacedEvent(String type, String orderId) {
        this.type = type;
        this.orderId = orderId;
    }
}
