package com.baemin.server.ceo.batch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class JobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job RankingJob() {
        return jobBuilderFactory.get( "rank-job" ) // 일억성이 되는 임의 잡 이름을 지정
                .flow( RankingStep() ) // 실행하는 Step을 지정
                .end()
                .build();
    }

    @Bean
    public Step RankingStep() {
        return null;
    }
}
