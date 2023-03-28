package com.wick.store.domain.entiey;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("t_product_publish")
public class PublishApproveEntity extends BaseEntity implements Serializable {
    /** 产品单号 */
    private String pubCode;
    /** 产品id */
    private String productId;
    /** 审批状态 */
    private Integer approveStatus;
    /** 审批人 */
    private String approver;
    /** 产品名字 */
    private String productName;
    /** 发布者id */
    private String userId;
    /** 分类id */
    private String cid;
    /** 审批通过时间 */
    private Date approveTime;
}
