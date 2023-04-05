package com.wick.store.domain.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public  class SubscribeApproveNode {

    @ApiModelProperty("订单号")
    private String subCode;

    @ApiModelProperty("审批节点名称")
    private String wfNodeId;

    @ApiModelProperty("获取分类id")
    private String cid;

}
