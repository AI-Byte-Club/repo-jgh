package com.example.indexpractice.controller;

import com.example.indexpractice.service.SeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SeedController {

    private final SeedService seedService;

    @PostMapping("/seed/{table}")
    public Map<String, Object> seed(@PathVariable String table, @RequestParam(defaultValue = "10000") int amount) {
        seedService.seedTable(table, amount);
        return Map.of("success", true, "count", seedService.getCount(table));
    }

    @PostMapping("/reset/{table}")
    public Map<String, Object> reset(@PathVariable String table) {
        seedService.resetTable(table);
        return Map.of("success", true, "count", 0);
    }

    @GetMapping("/count/{table}")
    public Map<String, Object> getCount(@PathVariable String table) {
        return Map.of("count", seedService.getCount(table));
    }
}
