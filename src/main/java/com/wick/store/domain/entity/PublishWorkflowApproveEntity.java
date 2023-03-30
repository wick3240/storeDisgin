package com.wick.store.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("workflow_publish_approve")
public class PublishWorkflowApproveEntity extends BaseEntity implements Serializable {
    /** 发布单号 */
    private String pubCode;
    /** 节点审批状态 */
    private Integer nodeApproveStatus;
    /** 节点审批人 */
    private String approver;
    /** 分类id */
    private String cid;
    /** node节点审批人 */
    private String userList;
    /** 审批流id */
    private String workflowId;
    /** 审批流节 */
    private String nodeId;
}