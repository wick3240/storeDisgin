package com.wick.store.domain.entiey;

import com.wick.store.eums.AccountType;
import lombok.Data;

@Data
public class JwtTokenEntity {
    private String username;
    private String jwtToken;
    private String refreshToken;
    private byte[] authentication;
    private String id;
    private AccountType loginType;

    public JwtTokenEntity() {
    }

    public JwtTokenEntity(String id, AccountType loginType, String username,
                          String jwtToken, String refreshToken, byte[] authentication) {
        this.id = id;
        this.loginType = loginType;
        this.username = username;
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
        this.authentication = authentication;
    }
}
