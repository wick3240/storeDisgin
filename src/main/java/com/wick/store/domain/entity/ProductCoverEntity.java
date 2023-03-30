package com.wick.store.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("product_cover")
public class ProductCoverEntity extends BaseEntity implements Serializable {
    /**
     * 名称
     */
    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 状态
     */
    private Integer status;
}
