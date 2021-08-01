package com.baemin.server.ceo.board.service;

import com.baemin.server.ceo.board.data.HackerNews;
import com.baemin.server.ceo.board.util.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class BoardService {

    private static final Logger logger = LoggerFactory.getLogger( BoardService.class );

    private static final String HACKER_NEWS_URL = "https://hacker-news.firebaseio.com";
    private WebClient webClient;

    public ResponseEntity getNews() {

        this.webClient = WebClient.builder()
                .baseUrl( HACKER_NEWS_URL )
                .build();

        Long[] ids = webClient.get()
                .uri( "/v0/newstories.json?print=pretty&orderBy=\"$key\"&limitToFirst=10" )
                .retrieve()
                .bodyToMono( Long[].class )
                .block();

        if (ids == null) {
            logger.error( "new stories get items id is null" );
            return RestResponse.fail( HttpStatus.INTERNAL_SERVER_ERROR, "new stories get items id is nul" );
        }

        List<HackerNews> hackerNewsList = new ArrayList<>();

        for ( long itemId : ids ) {
            CompletableFuture<HackerNews> future = this.getNewsItems( itemId );
            HackerNews hackerNews = null;
            try {
                hackerNews = future.get();
            } catch ( Exception e ) {
                logger.error( "[CompletableFuture HackerNews get] itemId: {}, error: {}", itemId, e.getMessage() );
                continue;
            }
            hackerNewsList.add( hackerNews );
        }

        return RestResponse.success( hackerNewsList );
    }

    private CompletableFuture<HackerNews> getNewsItems( long itemId ) {

        CompletableFuture<HackerNews> future = new CompletableFuture();

        new Thread( () -> {
            Mono<HackerNews> response = this.webClient.get()
                    .uri( "/v0/item/" + itemId + ".json" )
                    .retrieve()
                    .bodyToMono( HackerNews.class )
                    .log();
            response.subscribe( future::complete );
        } ).start();

        return future;
    }
}
