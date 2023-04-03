package com.wick.store.domain.Dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductCoverDto extends BasePageDto{
    private String id;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 名称
     */
    @NotBlank(message = "name cannot be empty")
    private String name;
    /**
     * 分类排序
     */
    private Integer sort;
    /**
     * 图片地址
     */
    private String url;

}
