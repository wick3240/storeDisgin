package com.wick.store.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SubscribeRecordAndProductVO extends SubscribeRecordVO{

    /**
     * 订阅单号
     */
    private String subCode;
    /**
     * 工作流节点 id
     */
    private String nodeId;

    /**
     * workflow的id
     */
    private String workflowId;

    /**
     * 当前workflow对应的cid
     */
    private String cid;
    /**
     * 工作流节点 状态
     */
    private String nodeApprovalStatus;



}
