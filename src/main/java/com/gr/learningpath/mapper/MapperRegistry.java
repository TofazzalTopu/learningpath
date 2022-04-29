package com.gr.learningpath.mapper;

public interface MapperRegistry {
    <S, T> Mapper<S, T> getMapper(Class<S> sourceClass, Class<T> targetClass);

    <S, T> void addMapper(Class<S> sourceClass, Class<T> targetClass, Mapper<S, T> mapper);
}
