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

    private Integer getSize() {
        return size;
    }
    public static PageSizeOptions of(Integer pageSize) {
        for (PageSizeOptions option : PageSizeOptions.values()) {
            if (option.getSize().equals(pageSize)) return option;
        }
        return null;
    }
}
