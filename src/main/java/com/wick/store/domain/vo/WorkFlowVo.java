package com.wick.store.domain.vo;

import lombok.Data;

@Data
public class WorkFlowVo {
    /**
     * workflow的审批流
     */
    private String workflowFormula;
    /**
     * workflowId
     */
    private String workflowId;
}
