package com.example.feat006.service;

import com.example.feat006.entity.ProcessedMessage;
import com.example.feat006.repository.ProcessedMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DedupService {

    private final ProcessedMessageRepository processedMessageRepository;

    @Transactional
    public Map<String, Object> process(String messageId, String content) {
        if (processedMessageRepository.findByMessageId(messageId).isPresent()) {
            log.info("[Dedup] 중복 메시지 무시 - messageId: {}", messageId);
            return Map.of("messageId", messageId, "processed", false, "reason", "이미 처리된 메시지입니다");
        }

        log.info("[Dedup] 메시지 처리 - messageId: {}, content: {}", messageId, content);

        ProcessedMessage record = new ProcessedMessage();
        record.setMessageId(messageId);
        record.setProcessedAt(LocalDateTime.now());
        processedMessageRepository.save(record);

        return Map.of("messageId", messageId, "content", content, "processed", true,
                "processedAt", record.getProcessedAt().toString());
    }
}
