package com.example.feat006.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "outbox")
@Data
@NoArgsConstructor
public class Outbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messageId;
    private String messageType;

    @Column(columnDefinition = "TEXT")
    private String payload;

    private String status;
    private int failCount;
    private LocalDateTime occurredAt;
    private LocalDateTime processedAt;
}
