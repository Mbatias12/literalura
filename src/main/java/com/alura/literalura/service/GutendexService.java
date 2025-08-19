package com.alura.literalura.service;

import com.alura.literalura.dto.GutendexBookDto;
import com.alura.literalura.dto.GutendexResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class GutendexService {

    private final WebClient webClient;

    public GutendexService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://gutendex.com").build();
    }

    public Mono<GutendexBookDto> searchFirstByTitle(String title) {
        String encoded = URLEncoder.encode(title, StandardCharsets.UTF_8);
        return webClient.get()
                .uri("/books/?search=" + encoded)
                .retrieve()
                .bodyToMono(GutendexResponse.class)
                .map(response -> {
                    List<GutendexBookDto> results = response.getResults();
                    if (results == null || results.isEmpty()) return null;
                    return results.get(0);
                });
    }
}