package com.cjunn.setool.core.generator;

public interface IGenerator<T> {
    T generate(Class<?> clz);
    T next();
}
