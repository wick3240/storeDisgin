package com.wick.store.service;

import com.wick.store.domain.entiey.UserEntity;

public interface UserService {
    void reg(UserEntity userEntity);

    UserEntity login(String username, String password);
}
