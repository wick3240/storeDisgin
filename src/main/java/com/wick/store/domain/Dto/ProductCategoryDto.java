package com.wick.store.domain.Dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductCategoryDto extends BasePageDto{
    /**
     * 分类编号
     */
    @ApiModelProperty("id")
    private String id;
    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    @NotBlank(message = "name cannot be empty")
    private String name;
    /**
     * 分类描述
     */
    @ApiModelProperty("分类描述")
    private String description;

}
