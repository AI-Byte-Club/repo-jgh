package com.example.indexpractice.controller;

import com.example.indexpractice.dto.IndexInfoDto;
import com.example.indexpractice.service.IndexService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/index")
@RequiredArgsConstructor
public class IndexController {

    private final IndexService indexService;

    @PostMapping("/add")
    public Map<String, Object> addIndex(@RequestBody IndexInfoDto dto) {
        indexService.addIndex(dto.getTable(), dto.getIndexName(), dto.getColumns());
        return Map.of("success", true);
    }

    @PostMapping("/drop")
    public Map<String, Object> dropIndex(@RequestBody IndexInfoDto dto) {
        indexService.dropIndex(dto.getTable(), dto.getIndexName());
        return Map.of("success", true);
    }

    @GetMapping("/list/{table}")
    public List<Map<String, Object>> list(@PathVariable String table) {
        return indexService.getIndexes(table);
    }
}
