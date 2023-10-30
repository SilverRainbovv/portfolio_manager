package com.didenko.entity;

public enum PageSizeOptions {
    ONE(1),
    FIVE(5),
    TEN(10),
    FIFTEEN(15),
    TWENTY_FIVE(25);
    public final Integer size;
    private PageSizeOptions(Integer size){
        this.size = size;
    }
}
