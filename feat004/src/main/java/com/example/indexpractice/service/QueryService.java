package com.example.indexpractice.service;

import com.example.indexpractice.dto.QueryResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryService {

    private final JdbcTemplate jdbcTemplate;

    public QueryResultDto runQuery(String sql) {
        long start = System.currentTimeMillis();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        long end = System.currentTimeMillis();

        return QueryResultDto.builder()
                .rows(rows.stream().limit(20).toList())
                .rowCount(rows.size())
                .executionTimeMs(end - start)
                .build();
    }

    public List<Map<String, Object>> explainQuery(String sql) {
        return jdbcTemplate.queryForList("EXPLAIN " + sql);
    }
}
