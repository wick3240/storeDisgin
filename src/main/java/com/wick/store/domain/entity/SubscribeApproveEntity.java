package com.wick.store.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("subscribe_approve")
public class SubscribeApproveEntity extends BaseEntity implements Serializable {
    /** 订阅单号 */
    private String subCode;
    /** 产品id */
    private String productId;
    /** 审批状态 */
    private Integer approveStatus;
    /** 审批人 */
    private String approver;
    /** 产品名字 */
    private String productName;
    /** 分类id */
    private String cid;
    /** 审批通过时 */
    private Date approveTime;

}
