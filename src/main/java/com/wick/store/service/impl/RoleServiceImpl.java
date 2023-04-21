package com.wick.store.service.impl;

import com.wick.store.domain.vo.UserVo;
import com.wick.store.repository.UserMapper;
import com.wick.store.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<UserVo> getAllUser() {
        return userMapper.selectAllUser();
    }
}
