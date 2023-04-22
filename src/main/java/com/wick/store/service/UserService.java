package com.wick.store.service;

import com.wick.store.domain.Dto.ChangInfoPasswordDto;
import com.wick.store.domain.entity.UserEntity;
import com.wick.store.domain.vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface UserService {
    void reg(UserEntity userEntity);

    Map<String,Object> login(String username, String password);

    void changePassword(ChangInfoPasswordDto changInfoPasswordDto);

    UserEntity findByUid(String uid);

    void changeInfoUser(String uid,UserEntity user);


    void updateAvatar(HttpSession session,String uid, String username, MultipartFile file);


    void logout(String userId);

    UserVo getUserId(String id);

    UserEntity userInfo(String token);

    void deletedByUser(String id);
}
