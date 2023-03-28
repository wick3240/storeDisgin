package com.wick.store.domain.vo;

import lombok.Data;

@Data
public class ProductBannerProductVo {
    /**
     * 产品id
     */
    private String id;

    /**
     * 产品名字
     */
    private String name;

    /**
     * 产品图片
     *
     */
    private String img;
    /**
     * 排序
     */
    private String sort;

}
