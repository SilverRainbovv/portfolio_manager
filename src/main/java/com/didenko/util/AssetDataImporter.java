package com.didenko.util;

import java.math.BigDecimal;
import java.util.Optional;

public interface AssetDataImporter {

    BigDecimal getAssetPrice(String assetName);

}
