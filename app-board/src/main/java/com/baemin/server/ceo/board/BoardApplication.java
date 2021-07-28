package com.baemin.server.ceo.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.baemin.server.ceo.core"})
@EnableJpaRepositories(basePackages = {"com.baemin.server.ceo.core"})
public class BoardApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
    }
}