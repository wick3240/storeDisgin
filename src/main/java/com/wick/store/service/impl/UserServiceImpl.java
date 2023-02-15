package com.wick.store.service.impl;

import com.wick.store.domain.entiey.UserEntity;
import com.wick.store.repository.UserMapper;
import com.wick.store.service.UserService;
import com.wick.store.service.ex.InsertException;
import com.wick.store.service.ex.PasswordNotMatchException;
import com.wick.store.service.ex.UserNameDuplicatedException;
import com.wick.store.util.GetPassWord;
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
        //加密操作
        String oldPassword =userEntity.getPassword();
        String salt=userEntity.getSalt();
        String md5Password=GetPassWord.getmd5PassWord(oldPassword,salt);
        userEntity.setPassword(md5Password);
        userEntity.setSalt(salt);
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

    @Override
    public UserEntity login(String username, String password) {
        UserEntity result=userMapper.findByUserName(username);
        if (result !=null){
            throw new UserNameDuplicatedException("用户数据不存在");
        }
        /**
         * 检验用户的密码是否匹配
         * 1：先获取数据库中加密后的密码
         * 2：和用户传递过来的密码进行比较
         * 2.1：先获取盐值
         * 2.2：将获取的用户的密码按照相同的md5算法进行加密
         */
        String oldPassword=result.getPassword();
        String salt =result.getSalt();
        String newMd5Password=GetPassWord.getmd5PassWord(password,salt);
        if (!newMd5Password.equals(oldPassword)){
            throw new PasswordNotMatchException("用户密码错误");
        }
        if (result.getIsDelete()==1){
            throw new UserNameDuplicatedException("用户数据不存在");

        }
        UserEntity user=new UserEntity();
        user.setUsername(result.getUsername());
        user.setUid(result.getUid());
        user.setAvatar(result.getAvatar());
        return user;
    }
}
