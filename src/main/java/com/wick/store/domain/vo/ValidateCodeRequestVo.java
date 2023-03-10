package com.wick.store.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ValidateCodeRequestVo implements Serializable {
    @NotBlank
    private String code;
    @NotBlank(message = "redirect_uri cannot be empty")
    @JsonProperty("redirect_uri")
    private String redirectUri;
}
