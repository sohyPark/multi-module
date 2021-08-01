package com.baemin.server.ceo.board.service;

import com.baemin.server.ceo.board.data.HackerNews;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class BoardServiceTest {

    private static final String HACKER_NEWS_URL = "https://hacker-news.firebaseio.com";


    @Test
    CompletableFuture<HackerNews> getNewsItems(long id) {

        WebClient webClient = WebClient.builder()
                .baseUrl( HACKER_NEWS_URL )
                .build();

        CompletableFuture<HackerNews> future = new CompletableFuture();

        new Thread( () -> {
            Mono<HackerNews> response = webClient.get()
                    .uri( "/v0/item/"+id+".json" )
                    .retrieve()
                    .bodyToMono(HackerNews.class)
                    .log();
            response.subscribe( future::complete );
        }).start();

        return future;
    }

    @Test
    void getNewsIds() throws ExecutionException, InterruptedException, JSONException {

        WebClient webClient = WebClient.builder()
                .baseUrl( HACKER_NEWS_URL )
                .build();

        Long[] ids = webClient.get()
                .uri( "/v0/newstories.json?print=pretty&orderBy=\"$key\"&limitToFirst=10" )
                .retrieve()
                .bodyToMono(Long[].class)
                .block();

        List<CompletableFuture<HackerNews>> list = new ArrayList<>();
        for (long id : ids) {
            CompletableFuture<HackerNews> future = this.getNewsItems(id);
            list.add( future );
        }

        Assertions.assertEquals(10, list.size());
    }
}