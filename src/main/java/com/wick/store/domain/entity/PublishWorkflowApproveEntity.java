package com.wick.store.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("workflow_publish_approve")
public class PublishWorkflowApproveEntity extends BaseEntity implements Serializable {
    /** 发布单号 */
   // @TableField(value = "pub_code")
    private String pubCode;
    /** 节点审批状态 */
  //  @TableField(value = "node_approve_status")
    private Integer nodeApproveStatus;
    /** 节点审批人 */
   // @TableField(value = "node_approver")
    private String nodeApprover;
    /** 分类id */
    private String cid;
    /** node节点审批人 */
   // @TableField(value = "user_list")
    private String userList;
    /** 审批流id */
   // @TableField(value = "workflow_id")
    private Integer workflowId;
    /** 审批流节 */
   // @TableField(value = "node_id")
    private String nodeId;
    /**
     * 审批时间
     */
   // @TableField(value = "approve_time")
    private String approveTime;
    /**
     * 审批人
     */
    private String ownerName;
}
