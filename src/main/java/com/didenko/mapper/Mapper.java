package com.didenko.mapper;

public interface Mapper <F, T>{

    T mapFrom(F object);

}
