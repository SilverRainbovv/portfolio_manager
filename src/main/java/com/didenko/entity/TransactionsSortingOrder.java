package com.didenko.entity;

public enum TransactionsSortingOrder {
    ASSET_NAME("Asset name", "asset_name"),
    POSITION_DIRECTION("Position direction", "positionDirection"),
    OPEN_TIME("Open time", "openDate"),
    OPEN_PRICE("Open price", "openPrice"),
    VOLUME("Volume", "quantity");

    public final String order;
    public final String fieldName;

    private TransactionsSortingOrder(String order, String fieldName){
        this.order = order;
        this.fieldName = fieldName;
    }

}
