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

    @ApiModelProperty("分类公式")
    private String workflowFormula;

    @ApiModelProperty("分类数据接口")
    private String url;

    @ApiModelProperty("公式id")
    private Integer workflowId;

}
