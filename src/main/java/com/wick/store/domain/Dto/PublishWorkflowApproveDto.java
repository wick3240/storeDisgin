package com.wick.store.domain.Dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PublishWorkflowApproveDto {
    @ApiModelProperty(value = "节点审批状态")
    private Integer nodeApproveStatus;

    @ApiModelProperty(value = "节点approve的人")
    private String nodeApprover;

    @ApiModelProperty(value = "分类id")
    private String cid;

    @ApiModelProperty(value = "审批流id")
    private Integer workflowId;

    @ApiModelProperty(value = "审批通过时间")
    private Date approvalTime;

    @ApiModelProperty(value = "拿到封装的信息")
    private List<PublishApproveNode> publishApproveNodes;

}
