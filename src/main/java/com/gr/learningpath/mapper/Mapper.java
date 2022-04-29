package com.gr.learningpath.mapper;

@FunctionalInterface
public interface Mapper<S, T> {
    T map(S source);
}
