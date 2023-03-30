package com.wick.store.domain.vo;

import lombok.Data;

@Data
public class ProductHotVo {
    /**
     * 产品id
     */
    private String id;
    /**
     * 产品名字
     */
    private String name;
    /**
     * 产品分类
     */
    private String cid;
    /**
     * 订阅权重
     */
    private Integer weight;
    /**
     * 产品图片
     */
    private String img;



}
