package com.wick.store.domain.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SubscribeWorkflowApproveDto {
    @ApiModelProperty(value = "节点审批状态")
    private Integer nodeApproveStatus;

    @ApiModelProperty(value = "节点approve的人")
    private String nodeApprover;

    @ApiModelProperty(value = "审批流id")
    private Integer workflowId;

    @ApiModelProperty(value = "审批通过时间")
    private Date approvalTime;

    @ApiModelProperty(value = "拿到封装的信息")
    private List<SubscribeApproveNode> SubscribeApproveNodes;

}
