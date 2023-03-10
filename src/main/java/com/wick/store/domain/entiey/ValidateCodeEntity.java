package com.wick.store.domain.entiey;

import com.wick.store.eums.AccountType;
import lombok.Data;

@Data
public class ValidateCodeEntity {
    private String code;
    private String redirectUri;
    private byte[] authentication;
    private AccountType loginType;

    public ValidateCodeEntity() {
    }

    public ValidateCodeEntity( String code, AccountType loginType,
                              String redirectUri, byte[] authentication) {

        this.code = code;
        this.loginType = loginType;
        this.redirectUri = redirectUri;
        this.authentication = authentication;
    }
}
