package com.example.indexpractice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexInfoDto {
    private String table;
    private String indexName;
    private String[] columns;
}
