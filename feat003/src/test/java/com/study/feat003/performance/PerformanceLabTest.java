package com.study.feat003.performance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PerformanceLabTest {

    // 1. DB Connection Pool 실습
    @Test
    @DisplayName("1. DB Connection Pool: 매번 생성 vs Pool 사용 시뮬레이션")
    void connectionPoolTest() throws InterruptedException {
        int iterations = 1000;
        StopWatch stopWatch = new StopWatch();

        // [A] 매번 새 커넥션 생성 (TCP 핸드셰이크 + 인증 시뮬레이션)
        stopWatch.start("New Connection per Request");
        for (int i = 0; i < iterations; i++) {
            simulateCreateConnection(); 
        }
        stopWatch.stop();

        // [B] Pool에서 커넥션 획득 (이미 맺어진 연결 재사용)
        stopWatch.start("Reuse Connection (Pool)");
        for (int i = 0; i < iterations; i++) {
            simulateGetConnectionFromPool();
        }
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
    }

    private void simulateCreateConnection() throws InterruptedException {
        Thread.sleep(5); // 네트워크 왕복 및 인증 비용 5ms 가정
    }

    private void simulateGetConnectionFromPool() throws InterruptedException {
        Thread.sleep(0); // 실제로는 아주 미세한 시간이 걸림 (나노초 단위)
    }

    // 2. Cache (Local vs DB) 실습
    @Test
    @DisplayName("2. Cache: 로컬 맵 캐시 vs DB 조회 시뮬레이션")
    void cacheTest() throws InterruptedException {
        Map<String, String> localCache = new ConcurrentHashMap<>();
        int iterations = 100;
        StopWatch stopWatch = new StopWatch();

        // [A] DB 조회 (Network I/O 시뮬레이션)
        stopWatch.start("DB Query (No Cache)");
        for (int i = 0; i < iterations; i++) {
            simulateDbQuery("user_1");
        }
        stopWatch.stop();

        // [B] 로컬 캐시 조회
        localCache.put("user_1", "User Data");
        stopWatch.start("Local Cache Hit");
        for (int i = 0; i < iterations; i++) {
            String data = localCache.get("user_1");
        }
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
    }

    private String simulateDbQuery(String key) throws InterruptedException {
        Thread.sleep(50); // DB 쿼리 실행 시간 50ms 가정
        return "Data from DB";
    }

    // 3. 데이터 압축 처리 실습
    @Test
    @DisplayName("3. Data Processing: 일반 텍스트 vs GZIP 압축")
    void dataCompressionTest() throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb.append("This is some repetitive data to compress. Line number: ").append(i).append("\n");
        }
        byte[] rawData = sb.toString().getBytes();

        StopWatch stopWatch = new StopWatch();
        
        // [A] 압축 처리 시간 측정
        stopWatch.start("GZIP Compression");
        ByteArrayOutputStream obj = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(obj);
        gzip.write(rawData);
        gzip.close();
        byte[] compressedData = obj.toByteArray();
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
        System.out.printf("Raw Size: %d bytes\n", rawData.length);
        System.out.printf("Compressed Size: %d bytes\n", compressedData.length);
        System.out.printf("Compression Ratio: %.2f%%\n", (double)compressedData.length / rawData.length * 100);
    }

    // 4. Garbage Collection (GC) 실습
    @Test
    @DisplayName("4. GC: 대용량 객체 유지 vs 즉시 해제 흐름")
    void gcFlowTest() {
        Runtime runtime = Runtime.getRuntime();
        long initialMemory = runtime.totalMemory() - runtime.freeMemory();
        
        System.out.println("--- GC Simulation Start ---");
        
        // [A] 객체 생성 및 유지 (Memory Pressure)
        List<Object> memoryLeakList = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            memoryLeakList.add(new byte[1024]); // 1KB씩 할당
            if (i % 20000 == 0) {
                System.out.printf("Allocated: %d items, Used Memory: %d MB\n", i, (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024));
            }
        }
        
        long memoryAfterAllocation = runtime.totalMemory() - runtime.freeMemory();
        
        // [B] 참조 해제 및 GC 유도
        memoryLeakList = null;
        System.gc(); // 강제 GC 호출 (실무에선 금기지만 테스트용)
        
        long memoryAfterGc = runtime.totalMemory() - runtime.freeMemory();
        
        System.out.println("Memory before GC: " + memoryAfterAllocation / (1024 * 1024) + " MB");
        System.out.println("Memory after GC: " + memoryAfterGc / (1024 * 1024) + " MB");
    }
}
