package com.wick.store.service.impl;

import com.wick.store.domain.entiey.JwtTokenEntity;
import com.wick.store.eums.AppConstant;
import com.wick.store.service.JwtTokenEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@CacheConfig(cacheNames = AppConstant.CACHE_JWT_TOKEN)
public class JwtTokenEntityServiceImpl implements JwtTokenEntityService {
    @Override
    @Cacheable(key = "#id", unless = "#result == null")
    public JwtTokenEntity getJwtTokenEntityById(String id) {
        log.debug("jwt token not in cache : {}", id);
        return null;
    }

    @Override
    @CachePut(key = "#entity.id")
    public JwtTokenEntity saveJwtToken(JwtTokenEntity entity) {
        return entity;
    }

    @Override
    @CacheEvict(key = "#id")
    public void removeJwtToken(String id) {

    }

    @Override
    @Cacheable(cacheNames = AppConstant.CACHE_REFRESH_TOKEN, key = "#refreshToken", unless = "#result == null")
    public JwtTokenEntity getRefreshToken(String refreshToken) {
        log.warn("refresh token not in cache : {}", refreshToken);
        return null;
    }

    @Override
    @CachePut(cacheNames = AppConstant.CACHE_REFRESH_TOKEN, key = "#entity.refreshToken")
    public JwtTokenEntity saveRefreshTokenToken(JwtTokenEntity entity) {
        return entity;
    }

    @Override
    @CacheEvict(cacheNames = AppConstant.CACHE_REFRESH_TOKEN, key = "#refreshToken")
    public void removeRefreshToken(String refreshToken) {

    }
}
