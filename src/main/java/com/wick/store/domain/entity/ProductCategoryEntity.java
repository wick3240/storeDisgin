package com.wick.store.domain.entity;


import com.baomidou.mybatisplus.annotation.TableField;
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
    @TableField(value = "workflow_formula")
    private String workflowFormula;

    /**
     *分类描述
     */
    private String description;
    /**
     * 数据api接口
     */
    private String url;
    /**
     * workflow_id
     */
    @TableField(value = "workflow_id")
    private Integer workflowId;

}
