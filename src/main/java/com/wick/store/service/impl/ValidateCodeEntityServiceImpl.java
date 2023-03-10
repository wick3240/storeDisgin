package com.wick.store.service.impl;

import com.wick.store.domain.entiey.ValidateCodeEntity;
import com.wick.store.service.ValidateCodeEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ValidateCodeEntityServiceImpl implements ValidateCodeEntityService {
    @Override
    @CachePut(key = "#entity.code")
    public ValidateCodeEntity save(ValidateCodeEntity entity) {
        return entity;
    }
    @Override
    @Cacheable(key = "#code", unless = "#result == null")
    public ValidateCodeEntity getValidateCodeEntityByCode(String code) {
        log.warn("validate code not in cache : {}", code);
        return null;
    }

    @Override
    @CacheEvict(key = "#code")
    public void remove(String code) {

    }


}
