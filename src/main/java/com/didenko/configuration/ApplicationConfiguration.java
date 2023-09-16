package com.didenko.configuration;

import com.didenko.util.AssetDataImporter;
import com.didenko.util.TwelveAssetDataImporter;import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootConfiguration
@PropertySource({"classpath:/apiKeys.properties", "classpath:/application.yml"})
public class ApplicationConfiguration {

    @Bean
    public AssetDataImporter assetDataImporter(@Value("${twelve.val}") String apiKey){
        return new TwelveAssetDataImporter(apiKey);
    }

}