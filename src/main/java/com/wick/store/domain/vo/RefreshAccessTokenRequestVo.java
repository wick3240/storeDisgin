package com.wick.store.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class RefreshAccessTokenRequestVo implements Serializable {
    @NotBlank(message = "refresh_token cannot be empty")
    @JsonProperty("refresh_token")
    private String refreshToken;
    private String scope;
}
