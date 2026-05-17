package com.example.indexpractice.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class QueryResultDto {
    private List<Map<String, Object>> rows;
    private long executionTimeMs;
    private int rowCount;
    private List<Map<String, Object>> explainResult;
}
