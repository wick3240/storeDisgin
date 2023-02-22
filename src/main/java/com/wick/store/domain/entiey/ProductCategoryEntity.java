package com.wick.store.domain.entiey;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_product_category")
public class ProductCategoryEntity extends BaseEntity implements Serializable {
    /**
     * 分类编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * workflow公式
     */
    private String workflow;

    /**
     *分类描述
     */
    private String description;

}
