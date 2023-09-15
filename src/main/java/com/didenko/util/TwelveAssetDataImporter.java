package com.didenko.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Data
@RequiredArgsConstructor
public class TwelveAssetDataImporter implements AssetDataImporter{
    private final String apiKey;

    @Override
    public BigDecimal getAssetPrice(String assetName) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://twelve-data1.p.rapidapi.com/price?symbol=" + assetName))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "twelve-data1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject data = new JSONObject(response.body());
            return new BigDecimal(data.getString("price"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
