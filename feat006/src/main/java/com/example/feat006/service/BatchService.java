package com.example.feat006.service;

import com.example.feat006.entity.BatchData;
import com.example.feat006.repository.BatchDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BatchService {

    private final BatchDataRepository batchDataRepository;

    @Transactional
    public int generate(int count) {
        List<BatchData> list = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            BatchData data = new BatchData();
            data.setContent("배치 데이터 #" + i);
            data.setSent(false);
            data.setCreatedAt(LocalDateTime.now());
            list.add(data);
        }
        batchDataRepository.saveAll(list);
        return count;
    }

    @Transactional
    public Map<String, Object> send() {
        List<BatchData> unsent = batchDataRepository.findBySent(false);
        for (BatchData data : unsent) {
            log.info("[Batch] 데이터 전송 처리 - id: {}, content: {}", data.getId(), data.getContent());
            data.setSent(true);
        }
        batchDataRepository.saveAll(unsent);
        return Map.of("processedCount", unsent.size(), "data", unsent);
    }

    public Map<String, Object> status() {
        long sent = batchDataRepository.countBySent(true);
        long unsent = batchDataRepository.countBySent(false);
        return Map.of("sent", sent, "unsent", unsent, "total", sent + unsent);
    }
}
