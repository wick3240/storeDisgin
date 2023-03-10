package com.wick.store.domain.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class ValidateTokenRequestVo implements Serializable {
    @NotBlank(message = "token cannot be empty")
    private String token;
}
