package com.didenko.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class TwelveAssetDataImporter implements AssetDataImporter{

    private final String apiKey;

    @Override
    public Map<String, BigDecimal> getAssetPrice(List<String> assetNames) {

        var resultMap = new HashMap<String, BigDecimal>();
        var namesToInput = String.join("%2C%20", assetNames);
        var objectMapper = new ObjectMapper();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://twelve-data1.p.rapidapi.com/price?symbol=" + namesToInput))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", "twelve-data1.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            var resultTree = objectMapper.readTree(response.body()).fields();

            while (resultTree.hasNext()){
                var node = resultTree.next();
                if(assetNames.size() == 1){
                   var price = node.getValue().asText();
                   resultMap.put(assetNames.get(0),new BigDecimal(price));
                }else {
                    var key = node.getKey();
                    resultMap.put(key, new BigDecimal(node.getValue().get("price").asText()));
                }
            }
            return resultMap;
        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            var result = new HashMap<String, BigDecimal>();
            assetNames.forEach(name -> result.put(name, new BigDecimal(50)));

            return result;
        }
    }
}
