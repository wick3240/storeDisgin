package com.wick.store.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductCategoryVo {
    private String id;
    /**
     * 分类名称
     */

    @NotBlank(message = "name cannot be empty")
    private String name;
    /**
     * 分类描述
     */

    private String description;

    /**
     * 公式
     */
    private String workflowFormula;

    /**
     * 数据api接口
     */
    private String url;
    /**
     * workflow_id
     */
    private Integer workflowId;


}
