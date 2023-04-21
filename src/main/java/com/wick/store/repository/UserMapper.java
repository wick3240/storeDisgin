package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wick.store.domain.entity.UserEntity;
import com.wick.store.domain.vo.UserVo;
import com.wick.store.util.JsonResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserMapper extends BaseMapper<UserEntity> {
    /**
     * 插入用户的数据
     * @param userEntity
     * @return 受影响的行数（增，删，改，查都受影响的行数作为返回值，可以根据返回值判断是否成功,mybatis-plus已经封装了普通的插入语句
     */
   // Integer insert(UserEntity userEntity);


    /**
     * 根据用户名来查询返回的数据
     * @param username
     * @return 如果找到的话，返回对应的用户的数据，没有就返回null
     */
    UserEntity findByUserName(String username);

    UserVo findByUid(String uid);

    Integer updatePasswordByUid( String username, String newPassword);

    Integer updateInfoByUid(UserEntity user);

    Integer updateAvatarByUid(String uid, String avatar, String username);

    UserEntity getUser(String username);

    List<UserVo> selectAllUser();
}
