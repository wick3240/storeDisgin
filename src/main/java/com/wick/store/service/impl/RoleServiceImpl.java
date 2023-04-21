package com.wick.store.service.impl;

import com.wick.store.domain.vo.UserVo;
import com.wick.store.repository.UserMapper;
import com.wick.store.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleServiceImpl implements RoleService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserVo getAllUser() {
        return userMapper.selectAllUser();
    }
}
