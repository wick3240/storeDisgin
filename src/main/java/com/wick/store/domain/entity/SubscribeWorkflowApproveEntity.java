package com.wick.store.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("workflow_subscribe_approve")
public class SubscribeWorkflowApproveEntity extends BaseEntity implements Serializable {
    /** 发布单号 */
    @TableField(value = "sub_code")
    private String subCode;
    /** 节点审批状态 */
    @TableField(value = "approve_status")
    private Integer nodeApprovalStatus;
    /** 节点审批人 */
    @TableField(value = "node_approver")
    private String nodeApprover;
    /** 分类id */
    private String cid;
    /** node节点审批人 */
    @TableField(value = "user_list")
    private String userList;
    /** 审批流id */
    @TableField(value = "workflow_id")
    private String workflowId;
    /** 审批流节点*/
    @TableField(value = "node_id")
    private String nodeId;
    /**
     * 审批时间
     */
    @TableField(value = "approval_time")
    private Date approvalTime;



}
