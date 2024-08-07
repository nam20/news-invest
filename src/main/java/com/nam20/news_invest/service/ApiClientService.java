package com.nam20.news_invest.service;

import com.nam20.news_invest.dto.NewsApiResponse;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class ApiClientService {

    private final Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();
    private final RestTemplate restTemplate;
    private final String newsApiUrl = "https://newsapi.org/v2/everything";
    private final String searchParam = "stock";

    public ApiClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public NewsApiResponse retrieveNews() {
        URI uri = UriComponentsBuilder.fromUriString(newsApiUrl)
                .queryParam("q", searchParam)
                .queryParam("apiKey", dotenv.get("NEWS_API_KEY"))
                .build()
                .toUri();

        return restTemplate.getForObject(uri, NewsApiResponse.class);
    }

}
