package com.example.feat006.repository;

import com.example.feat006.entity.BatchData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchDataRepository extends JpaRepository<BatchData, Long> {
    List<BatchData> findBySent(boolean sent);
    long countBySent(boolean sent);
}
