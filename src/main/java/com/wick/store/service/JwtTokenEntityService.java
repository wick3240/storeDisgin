package com.wick.store.service;

import com.wick.store.domain.entiey.JwtTokenEntity;

public interface JwtTokenEntityService {
    /**
     * 获取token
     *
     * @param id
     * @return
     */
    JwtTokenEntity getJwtTokenEntityById(String id);

    /**
     * 保存jwt token
     *
     * @param entity
     * @return
     */
    JwtTokenEntity saveJwtToken(JwtTokenEntity entity);

    /**
     * 移除token
     *
     * @param id
     * @return
     */
    void removeJwtToken(String id);

    /**
     * 获取刷新token
     *
     * @param refreshToken
     * @return
     */
    JwtTokenEntity getRefreshToken(String refreshToken);

    /**
     * 保存refresh token
     *
     * @param entity
     * @return
     */
    JwtTokenEntity saveRefreshTokenToken(JwtTokenEntity entity);


    /**
     * 移除token
     *
     * @param refreshToken
     * @return
     */
    void removeRefreshToken(String refreshToken);
}
