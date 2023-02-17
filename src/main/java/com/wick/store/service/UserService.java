package com.wick.store.service;

import com.wick.store.domain.entiey.UserEntity;

public interface UserService {
    void reg(UserEntity userEntity);

    UserEntity login(String username, String password);

    void changePassword(String uid, String username, String oldPassword, String newPassword);

    UserEntity findByUid(String uid);
}
