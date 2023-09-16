package com.didenko.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AssetDataImporter {

    Map<String, BigDecimal> getAssetPrice(List<String> assetName);

}
