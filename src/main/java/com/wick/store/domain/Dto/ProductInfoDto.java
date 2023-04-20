package com.wick.store.domain.Dto;

import lombok.Data;

@Data
public class ProductInfoDto extends BasePageDto{
    /**
     * 产品id
     */
    private String id;
    /** 产品名字 */
    private String name;
    /** 描述 */
    private String description;
    /** 分类id */
    private String cid;
    /** 产品状态 */
    private Integer status;
    /** 用于轮播图的排序 */
    private Integer sort;
    /** 产品单号 */
    private String pubCode;
    /** 产品图 */
    private String coverUrl;
    /**
     * 权重
     */
    private Integer weight;
    /**
     * 产品api
     */
    private String api;


}
