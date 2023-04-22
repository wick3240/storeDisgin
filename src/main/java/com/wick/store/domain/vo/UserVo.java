package com.wick.store.domain.vo;


import lombok.Data;

@Data
public class UserVo {
    /**
     * userId
     */
    private String id;

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
     * * 0是女，1是男
     */
    private Integer gender;
    /**
     *头像
     */
    private String avatar;
    /**
     * 是否删除
     */
    private int isDeleted;
    /**
     * 权限
     */
    private String roleCode;

}
