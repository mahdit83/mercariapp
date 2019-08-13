package com.mercari.data.mapper.base;

public interface DataMapper<E,D> {

    D transform(E e);
}
