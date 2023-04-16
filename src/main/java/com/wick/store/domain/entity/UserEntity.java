package com.wick.store.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value ="t_user")
public class UserEntity extends BaseEntity implements Serializable {
    /**
     * 用户账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐值，用于加密
     */
    private String salt;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 性别
     * 0是女，1是男
     */
    private Integer gender;
    /**
     *头像
     */
    private String avatar;

    private static final long serialVersionUID = 1L;
    /**
     * token过期时间
     */
    private long expireTime;
    /**
     * token 有效时间
     */
    private long loginTime;

}
    //实体类省略了三个方法，一个是get，set方法，一个是equals和hashCode()方法，一个是toString方法。






