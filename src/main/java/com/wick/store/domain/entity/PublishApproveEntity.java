package com.wick.store.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("publish_approve")
public class PublishApproveEntity extends BaseEntity implements Serializable {
    /** 产品单号 */
    @TableField(value = "pub_code")
    private String pubCode;
    /** 产品id */
    @TableField(value = "product_id")
    private String productId;
    /** 审批状态 */
    @TableField(value = "approve_status")
    private Integer approveStatus;
    /** 审批人 */
    private String approver;
    /** 产品名字 */
    @TableField(value = "product_name")
    private String productName;
    /** 分类id */
    private String cid;
    /** 审批通过时间 */
    @TableField(value = "approve_time")
    private Date approveTime;
}
