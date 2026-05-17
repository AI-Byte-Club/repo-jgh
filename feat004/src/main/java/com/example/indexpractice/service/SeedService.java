package com.example.indexpractice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeedService {

    private final JdbcTemplate jdbcTemplate;
    private final Random random = new Random();

    private static final String[] KOREAN_NAMES = {
        "김민준", "이서준", "박예준", "최도윤", "정시우", "강주원", "조하준", "윤준서", "장지호", "임도현",
        "한건우", "오유준", "서지훈", "신현우", "권민재", "황우진", "안지안", "송서진", "전연우", "홍유찬",
        "김지유", "이하윤", "박서윤", "최서연", "정하은", "강지우", "조수아", "윤서현", "장지민", "임예은",
        "한채원", "오하린", "서주아", "신다은", "권나은", "황나윤", "안소율", "송민서", "전서아", "홍지아",
        "김도준", "이민성", "박승우", "최현우", "정지환", "강동현", "조준혁", "윤도영", "장태양", "임성민"
    };

    private static final String[] ACTIVITY_TYPES = {"VISIT", "BUY", "LIKE", "SHARE"};
    private static final String[] STATES = {"RR", "CC", "DN"};
    private static final String[] STATUSES = {"W", "P", "D", "F"};

    public void resetTable(String table) {
        jdbcTemplate.execute("TRUNCATE TABLE " + table);
    }

    public void seedTable(String table, int amount) {
        switch (table) {
            case "article" -> seedArticle(amount);
            case "activityLog" -> seedActivityLog(amount);
            case "reservation" -> seedReservation(amount);
            case "jobqueue" -> seedJobQueue(amount);
            default -> throw new IllegalArgumentException("Unknown table: " + table);
        }
    }

    private void seedArticle(int amount) {
        String sql = "INSERT INTO article (category, writerId, title, content, regdt) VALUES (?, ?, ?, ?, ?)";
        List<Object[]> batchArgs = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            batchArgs.add(new Object[]{
                random.nextInt(1, 21),
                random.nextInt(1, 1001),
                "제목_" + random.nextInt(10000),
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
                java.sql.Timestamp.valueOf(LocalDate.of(2024, random.nextInt(1, 13), random.nextInt(1, 28)).atStartOfDay())
            });
            if (batchArgs.size() == 1000) {
                jdbcTemplate.batchUpdate(sql, batchArgs);
                batchArgs.clear();
            }
        }
        if (!batchArgs.isEmpty()) jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    private void seedActivityLog(int amount) {
        String sql = "INSERT INTO activityLog (userId, activityType, activityDate, activityDatetime, memo) VALUES (?, ?, ?, ?, ?)";
        List<Object[]> batchArgs = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            LocalDate date = LocalDate.of(2024, random.nextInt(1, 13), random.nextInt(1, 28));
            batchArgs.add(new Object[]{
                random.nextInt(1, 501),
                ACTIVITY_TYPES[random.nextInt(ACTIVITY_TYPES.length)],
                date,
                java.sql.Timestamp.valueOf(date.atTime(random.nextInt(24), random.nextInt(60))),
                random.nextBoolean() ? "Memo " + i : null
            });
            if (batchArgs.size() == 1000) {
                jdbcTemplate.batchUpdate(sql, batchArgs);
                batchArgs.clear();
            }
        }
        if (!batchArgs.isEmpty()) jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    private void seedReservation(int amount) {
        String sql = "INSERT INTO reservation (name, reserveDate, state, regDt) VALUES (?, ?, ?, ?)";
        List<Object[]> batchArgs = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            batchArgs.add(new Object[]{
                KOREAN_NAMES[random.nextInt(KOREAN_NAMES.length)],
                LocalDate.of(2024, random.nextInt(1, 13), random.nextInt(1, 28)),
                STATES[random.nextInt(STATES.length)],
                java.sql.Timestamp.valueOf(LocalDate.of(2024, random.nextInt(1, 13), random.nextInt(1, 28)).atStartOfDay())
            });
            if (batchArgs.size() == 1000) {
                jdbcTemplate.batchUpdate(sql, batchArgs);
                batchArgs.clear();
            }
        }
        if (!batchArgs.isEmpty()) jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    private void seedJobQueue(int amount) {
        String sql = "INSERT INTO jobqueue (jobId, status, createdAt) VALUES (?, ?, ?)";
        List<Object[]> batchArgs = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            String jobId = UUID.randomUUID().toString().substring(0, 16);
            batchArgs.add(new Object[]{
                jobId,
                STATUSES[random.nextInt(STATUSES.length)],
                java.sql.Timestamp.valueOf(LocalDate.of(2024, random.nextInt(1, 13), random.nextInt(1, 28)).atStartOfDay())
            });
            if (batchArgs.size() == 1000) {
                jdbcTemplate.batchUpdate(sql, batchArgs);
                batchArgs.clear();
            }
        }
        if (!batchArgs.isEmpty()) jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    public long getCount(String table) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + table, Long.class);
    }
}
