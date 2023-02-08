package com.wick.store.domain.entiey;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserEntity extends BaseEntity implements Serializable {
    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private Integer gender;
    private String avatar;
    private Integer isDelete;
}
    //实体类省略了三个方法，一个是get，set方法，一个是equals和hashCode()方法，一个是toString方法。






