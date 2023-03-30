package com.wick.store.domain.vo;


import lombok.Data;

@Data
public class ProductCoverVo {
    private String id;
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
