package com.example.indexpractice.controller;

import com.example.indexpractice.dto.QueryResultDto;
import com.example.indexpractice.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/query")
@RequiredArgsConstructor
public class QueryController {

    private final QueryService queryService;

    @PostMapping("/run")
    public QueryResultDto runQuery(@RequestBody Map<String, String> body) {
        return queryService.runQuery(body.get("sql"));
    }

    @PostMapping("/explain")
    public List<Map<String, Object>> explainQuery(@RequestBody Map<String, String> body) {
        return queryService.explainQuery(body.get("sql"));
    }

    @PostMapping("/custom")
    public ResponseEntity<?> customQuery(@RequestBody Map<String, String> body) {
        String sql = body.get("sql").trim().toUpperCase();
        if (!sql.startsWith("SELECT")) {
            return ResponseEntity.badRequest().body(Map.of("error", true, "message", "Only SELECT statements are allowed."));
        }
        try {
            return ResponseEntity.ok(queryService.runQuery(body.get("sql")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", true, "message", e.getMessage()));
        }
    }
}
