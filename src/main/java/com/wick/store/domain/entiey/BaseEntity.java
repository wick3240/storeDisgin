package com.wick.store.domain.entiey;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
@Data
public class BaseEntity implements Serializable {
     @TableField(value = "created_user", fill = FieldFill.INSERT)
     private String createdUser;

     @TableField(value = "created_time", fill = FieldFill.INSERT)
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private Date createdTime ;

     @TableField(value = "modified_user", fill = FieldFill.INSERT)
     private String modifiedUser ;

     @TableField(value = "modified_time", fill = FieldFill.INSERT)
     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
     private Date modifiedTime ;

}
