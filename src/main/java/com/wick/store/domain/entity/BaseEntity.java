package com.wick.store.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
@Data
public class BaseEntity implements Serializable {
     @TableId(value = "id", type = IdType.ASSIGN_UUID)
     private String id;
     @TableField(value = "created_user", fill = FieldFill.INSERT)
     private String createdUser;

     @TableField(value = "created_time", fill = FieldFill.INSERT)
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private Date createdDate ;

     @TableField(value = "update_user", fill = FieldFill.INSERT)
     private String updateUser;

     @TableField(value = "update_date", fill = FieldFill.INSERT)
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private Date updateDate;

     @TableField(value = "is_deleted", fill = FieldFill.INSERT)
     private Boolean isDeleted;

}
