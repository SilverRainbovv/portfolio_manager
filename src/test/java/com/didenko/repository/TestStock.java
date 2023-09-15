package com.didenko.repository;

import com.didenko.util.TwelveAssetDataImporter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;


@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class TestStock {

    private final TwelveAssetDataImporter twelveAssetDataImporter;

    @Test
    public void test(){
        var data = twelveAssetDataImporter.getAssetData("AAPL");
        System.out.println();
    }

}
