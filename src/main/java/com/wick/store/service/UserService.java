package com.wick.store.service;

import com.wick.store.domain.entity.UserEntity;
import com.wick.store.util.JsonResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService {
    void reg(UserEntity userEntity);

    Map<String,Object> login(String username, String password);

    void changePassword(String username, String oldPassword, String newPassword);

    UserEntity findByUid(String uid);

    void changeInfoUser(String uid,UserEntity user);


    void updateAvatar(HttpSession session,String uid, String username, MultipartFile file);


    void logout(String userId);

    JsonResult getUserId(String username);

    UserEntity userInfo(String token);

    void deletedByUser(String id);
}
