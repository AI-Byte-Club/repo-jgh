package com.example.indexpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class IndexPracticeApplication {

    private final JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(IndexPracticeApplication.class, args);
    }

    @EventListener(ApplicationStartedEvent.class)
    public void initDatabase() {
        log.info("Initializing Database Tables...");
        
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS article (
              id INT AUTO_INCREMENT PRIMARY KEY,
              category INT NOT NULL,
              writerId INT NOT NULL,
              title VARCHAR(255) NOT NULL,
              content TEXT NOT NULL,
              regdt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """);

        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS activityLog (
              id INT AUTO_INCREMENT PRIMARY KEY,
              userId INT NOT NULL,
              activityType VARCHAR(20) NOT NULL,
              activityDate DATE NOT NULL,
              activityDatetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
              memo VARCHAR(200)
            )
        """);

        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS reservation (
              id BIGINT AUTO_INCREMENT PRIMARY KEY,
              name VARCHAR(30) NOT NULL,
              reserveDate DATE NOT NULL,
              state CHAR(2) NOT NULL,
              regDt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """);

        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS jobqueue (
              jobId VARCHAR(16) PRIMARY KEY,
              status CHAR(1) NOT NULL,
              createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """);
        
        log.info("Database Initialization Completed.");
    }
}
