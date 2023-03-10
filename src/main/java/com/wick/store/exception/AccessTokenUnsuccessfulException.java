package com.wick.store.exception;

import com.wick.store.domain.vo.AccessTokenUnsuccessfulResponseVo;
import lombok.Data;

@Data
public class AccessTokenUnsuccessfulException extends RuntimeException {
    private final AccessTokenUnsuccessfulResponseVo vo;

    public AccessTokenUnsuccessfulException(AccessTokenUnsuccessfulResponseVo vo) {
        super(vo.getErrorDescription());
        this.vo = vo;
    }
}
