package com.wick.store.service;

import com.wick.store.domain.entiey.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

public interface UserService {
    void reg(UserEntity userEntity);

    UserEntity login(String username, String password);

    void changePassword(String uid, String username, String oldPassword, String newPassword);

    UserEntity findByUid(String uid);

    void changeInfoUser(String uid, UserEntity user);


    void updateAvatar(HttpSession session,String uid, String username, MultipartFile file);




}
