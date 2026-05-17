package com.example.indexpractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IndexService {

    private final JdbcTemplate jdbcTemplate;

    public void addIndex(String table, String indexName, String[] columns) {
        String cols = String.join(", ", columns);
        String sql = String.format("CREATE INDEX %s ON %s (%s)", indexName, table, cols);
        jdbcTemplate.execute(sql);
    }

    public void dropIndex(String table, String indexName) {
        String sql = String.format("DROP INDEX %s ON %s", indexName, table);
        jdbcTemplate.execute(sql);
    }

    public List<Map<String, Object>> getIndexes(String table) {
        return jdbcTemplate.queryForList("SHOW INDEX FROM " + table);
    }
}
