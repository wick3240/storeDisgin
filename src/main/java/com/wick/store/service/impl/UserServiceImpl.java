package com.wick.store.service.impl;

import com.wick.store.domain.entiey.UserEntity;
import com.wick.store.repository.UserMapper;
import com.wick.store.service.UserService;
import com.wick.store.service.ex.InsertException;
import com.wick.store.service.ex.UserNameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(UserEntity userEntity) {

        //首先先调用findByUsername，来判断用户是否被注册过。
        UserEntity result=userMapper.findByUserName(userEntity.getUsername());
        if (result !=null){
            throw new UserNameDuplicatedException("用户已经被注册了");
        }
        userEntity.setIsDelete(0);
        userEntity.setCreatedUser(userEntity.getUsername());
        userEntity.setCreatedTime(new Date());
        userEntity.setModifiedTime(new Date());
        userEntity.setModifiedUser(userEntity.getUsername());
        Integer row=userMapper.insert(userEntity);
        if (row !=1){
            throw new InsertException("用户在注册过程中产生为止异常");
        }

    }
}
