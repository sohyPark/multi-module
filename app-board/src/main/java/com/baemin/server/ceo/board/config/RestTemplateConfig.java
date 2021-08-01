package com.baemin.server.ceo.board.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RestTemplateConfig {

//    @Bean
//    public AsyncRestTemplate restTemplate( RestTemplateBuilder restTemplateBuilder) {
//        return restTemplateBuilder
//                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
//                .setConnectTimeout(Duration.ofMillis(5000)) // connection-timeout
//                .setReadTimeout( Duration.ofMillis(5000)) // read-timeout
//                .additionalMessageConverters(new StringHttpMessageConverter( Charset.forName("UTF-8")))
//                .build();
//    }
}
