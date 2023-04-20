package com.wick.store.domain.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateSubscribeRecordDto {
//    @ApiModelProperty(value = "订阅者id")
//    private String userId;

    @ApiModelProperty(value = "分类id")
    private String cid;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "订阅者")
    private String subscriber;

    @ApiModelProperty(value = "订阅者id")
    private String userId;

}
