package com.didenko.util;

import com.didenko.asset.AssetData;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Data
@RequiredArgsConstructor
public class TwelveAssetDataImporter implements AssetDataImporter{
    private final String apiKey;

    @Override
    public AssetData getAssetData(String assetName) {
        AssetData assetData = new AssetData();
        assetData.setName(assetName);
        JSONObject data;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://twelve-data1.p.rapidapi.com/price?symbol=" + assetName))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "twelve-data1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            data = new JSONObject(response.body());
            assetData.setPrice(data.getDouble("price"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return assetData;
    }
}
