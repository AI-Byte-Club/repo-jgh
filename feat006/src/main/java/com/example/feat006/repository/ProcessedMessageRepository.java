package com.example.feat006.repository;

import com.example.feat006.entity.ProcessedMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProcessedMessageRepository extends JpaRepository<ProcessedMessage, Long> {
    Optional<ProcessedMessage> findByMessageId(String messageId);
}
