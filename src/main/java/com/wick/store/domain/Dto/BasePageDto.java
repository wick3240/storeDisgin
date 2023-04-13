package com.wick.store.domain.Dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class BasePageDto {

    @Min(value = 1, message = "页码必须 >= 1")
    @ApiModelProperty(value = "页码", example = "0")
    @TableField(select = false)
    private Integer page = 1;

    @ApiModelProperty(value = "页行数", example = "5")
    @TableField(select = false)
    private Integer row = 5;

    @ApiModelProperty("排序字段")
    private String field;

    @ApiModelProperty("0升序 1降序")
    private String sortField;
}