package com.wick.store.domain.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("product_category")
public class ProductCategoryEntity extends BaseEntity implements Serializable {


    /**
     * 分类名称
     */
    private String name;

    /**
     * workflow公式
     */
    private String workflowFormula;

    /**
     *分类描述
     */
    private String description;

}
