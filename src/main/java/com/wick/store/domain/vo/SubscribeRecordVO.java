package com.wick.store.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SubscribeRecordVO extends PageVO{
    private String id;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "产品ID")
    private String productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "审批人")
    private String ownerName;

    @ApiModelProperty(value = "订阅审批通过时间")
    private Date approvedTime;

    @ApiModelProperty(value = "审核状态;0:待审批；1：已审批通过；2：审批未通过")
    private Integer approveStatus;

    @ApiModelProperty(value = "分类名字")
    private String cidName;



}
