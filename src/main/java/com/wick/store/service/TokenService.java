package com.wick.store.service;

import com.wick.store.domain.vo.AccessTokenResponseVo;
import com.wick.store.domain.vo.RefreshAccessTokenRequestVo;
import com.wick.store.domain.vo.ValidateCodeRequestVo;
import com.wick.store.domain.vo.ValidateTokenRequestVo;

public interface TokenService {
    /**
     * 创建token
     *
     * @param vo
     * @return
     */
    AccessTokenResponseVo createToken(ValidateCodeRequestVo vo);

    /**
     * 刷新token
     *
     * @param vo
     * @return
     */
    AccessTokenResponseVo refreshToken(RefreshAccessTokenRequestVo vo);

    /**
     * 验证token是否有效
     *
     * @param vo
     * @return
     */
    AccessTokenResponseVo validateToken(ValidateTokenRequestVo vo);
}
