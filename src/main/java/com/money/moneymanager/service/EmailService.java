package com.money.moneymanager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EmailService {

    private final WebClient webClient;

    public EmailService(
            WebClient.Builder builder,
            @Value("${brevo.api.key}") String apiKey,
            @Value("${brevo.api.url}") String apiUrl
    ) {
        this.webClient = builder
                .baseUrl(apiUrl)
                .defaultHeader("api-key", apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public void sendEmail(String to, String subject, String body) {

        Map<String, Object> payload = Map.of(
                "sender", Map.of(
                        "name", "Money Manager",
                        "email", "vivekwillcode@gmail.com"
                ),
                "to", List.of(
                        Map.of("email", to)
                ),
                "subject", subject,
                "htmlContent", body
        );

        webClient.post()
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(r -> log.info("Email sent to {}", to))
                .doOnError(e -> log.error("Email sending failed", e))
                .subscribe(); // async, non-blocking
    }
}
