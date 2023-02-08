package com.wick.store.domain.entiey;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class BaseEntity implements Serializable {
     private String createdUser;
     private Date createdTime ;
     private String modifiedUser ;
     private Date modifiedTime ;

}
