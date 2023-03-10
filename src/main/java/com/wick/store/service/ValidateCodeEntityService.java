package com.wick.store.service;

import com.wick.store.domain.entiey.ValidateCodeEntity;

public interface ValidateCodeEntityService {
    /**
     * 保存
     *
     * @param entity
     * @return
     */
    ValidateCodeEntity save(ValidateCodeEntity entity);

    /**
     * 获取
     *
     * @param code
     * @return
     */
    ValidateCodeEntity getValidateCodeEntityByCode(String code);

    /**
     * 移除
     *
     * @param code
     */
    void remove(String code);
}
