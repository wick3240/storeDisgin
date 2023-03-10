package com.wick.store.service;

public interface MessageStoreConversionService {
    /**
     * 徐序列化
     *
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     *
     * @param bytes
     * @return
     */
    Object deserialize(byte[] bytes);
}
