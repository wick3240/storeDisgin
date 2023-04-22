package com.wick.store.domain.Dto;

import lombok.Data;

@Data
public class ChangInfoPasswordDto {
    private String username;
    private String oldPassword;
    private String newPassword;
}
