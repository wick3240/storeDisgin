package com.wick.store.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("workflow_subscribe_approve")
public class SubscribeWorkflowApproveEntity extends BaseEntity implements Serializable {
    /** 发布单号 */
    private String subCode;
    /** 节点审批状态 */
    private Integer nodeApprovalStatus;
    /** 节点审批人 */
    private String nodeApprover;
    /** 分类id */
    private String cid;
    /** node节点审批人 */
    private String userList;
    /** 审批流id */
    private String workflowId;
    /** 审批流节点*/
    private String nodeId;
    /**
     * 审批时间
     */
    private Date approvalTime;



}
