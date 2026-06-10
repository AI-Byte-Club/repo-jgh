package com.example.feat006.service;

import com.example.feat006.entity.Order;
import com.example.feat006.entity.Outbox;
import com.example.feat006.repository.OrderRepository;
import com.example.feat006.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxService {

    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;

    @Transactional
    public Map<String, Object> create(String productName) {
        Order order = new Order();
        order.setProductName(productName);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);

        Outbox outbox = buildOutbox("ORDER_PLACED",
                "{\"orderId\":" + order.getId() + ",\"product\":\"" + productName + "\"}");
        outboxRepository.save(outbox);

        return Map.of("orderId", order.getId(), "outboxId", outbox.getId(), "messageId", outbox.getMessageId(), "status", "WAITING");
    }

    @Transactional
    public Map<String, Object> createFailTest(String productName) {
        Order order = new Order();
        order.setProductName(productName);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);

        Outbox outbox = buildOutbox("FAIL_TEST",
                "{\"orderId\":" + order.getId() + ",\"product\":\"" + productName + "\",\"failTest\":true}");
        outboxRepository.save(outbox);

        return Map.of("orderId", order.getId(), "outboxId", outbox.getId(),
                "note", "/outbox/process 호출마다 실패 → 3회 후 FAILED 상태로 변경");
    }

    @Transactional
    public List<Map<String, Object>> process() {
        List<Outbox> waitingList = outboxRepository.findByStatus("WAITING");
        List<Map<String, Object>> results = new ArrayList<>();

        for (Outbox outbox : waitingList) {
            try {
                if ("FAIL_TEST".equals(outbox.getMessageType())) {
                    throw new RuntimeException("의도적 처리 실패 (FAIL_TEST)");
                }
                log.info("[Outbox] 메시지 처리 완료 - id: {}, payload: {}", outbox.getId(), outbox.getPayload());
                outbox.setStatus("DONE");
                outbox.setProcessedAt(LocalDateTime.now());
                results.add(Map.of("id", outbox.getId(), "status", "DONE", "messageType", outbox.getMessageType()));
            } catch (Exception e) {
                outbox.setFailCount(outbox.getFailCount() + 1);
                if (outbox.getFailCount() >= 3) {
                    outbox.setStatus("FAILED");
                    log.error("[Outbox] 최대 실패 횟수 초과 FAILED 처리 - id: {}", outbox.getId());
                } else {
                    log.warn("[Outbox] 처리 실패 {}회 - id: {}", outbox.getFailCount(), outbox.getId());
                }
                results.add(Map.of("id", outbox.getId(), "status", outbox.getStatus(), "failCount", outbox.getFailCount()));
            }
            outboxRepository.save(outbox);
        }
        return results;
    }

    public List<Outbox> list() {
        return outboxRepository.findAll();
    }

    private Outbox buildOutbox(String messageType, String payload) {
        Outbox outbox = new Outbox();
        outbox.setMessageId(UUID.randomUUID().toString());
        outbox.setMessageType(messageType);
        outbox.setPayload(payload);
        outbox.setStatus("WAITING");
        outbox.setFailCount(0);
        outbox.setOccurredAt(LocalDateTime.now());
        return outbox;
    }
}
