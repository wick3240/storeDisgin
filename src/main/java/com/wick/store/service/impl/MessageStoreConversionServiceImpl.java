package com.wick.store.service.impl;

import com.wick.store.service.MessageStoreConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.stereotype.Service;


@Service
public class MessageStoreConversionServiceImpl implements MessageStoreConversionService {
    private final GenericConversionService conversionService = createDefaultConversionService();

    @Override
    public byte[] serialize(Object object) {
        return (byte[]) this.conversionService.convert(object, TypeDescriptor.valueOf(Object.class),
                TypeDescriptor.valueOf(byte[].class));
    }

    @Override
    public Object deserialize(byte[] bytes) {
        return this.conversionService.convert(bytes, TypeDescriptor.valueOf(byte[].class),
                TypeDescriptor.valueOf(Object.class));
    }

    private static GenericConversionService createDefaultConversionService() {
        GenericConversionService converter = new GenericConversionService();
        converter.addConverter(Object.class, byte[].class, new SerializingConverter());
        converter.addConverter(byte[].class, Object.class, new DeserializingConverter());
        return converter;
    }
}
