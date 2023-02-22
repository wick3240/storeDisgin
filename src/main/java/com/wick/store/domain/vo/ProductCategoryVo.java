package com.wick.store.domain.vo;

import lombok.Data;

@Data
public class ProductCategoryVo {
    /**
     * 分类编号
     */
    private String cid;

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
