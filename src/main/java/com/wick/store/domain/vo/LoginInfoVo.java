package com.wick.store.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wick.store.eums.AccountType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
@Data
public class LoginInfoVo implements Serializable {
    @NotBlank(message = "login_type cannot be empty")
    @JsonProperty("login_type")
    private AccountType loginType;
    @JsonProperty("redirect_uri")
    private String redirectUri;
    private String state;

    public LoginInfoVo() {
    }

    public LoginInfoVo( AccountType loginType) {

        this.loginType = loginType;
    }

    public LoginInfoVo(AccountType loginType, String redirectUri, String state) {
        this.loginType = loginType;
        this.redirectUri = redirectUri;
        this.state = state;
    }
}
