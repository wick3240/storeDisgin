package com.wick.store.domain.Dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PublishApproveDto extends BasePageDto{
    /** 产品单号 */
    private String pubCode;
    /** 产品id */
    private String productId;
    /** 审批状态 前端传参给后端查询*/
    private List<Integer> StatusList;
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
    /**
     * 审批状态
     */
    private Integer Status;
    /**
     * 分类名字
     */
    private String cidName;

}
