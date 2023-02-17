package com.wick.store.domain.Dto;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowHandleNodeDto {
    private String nodeId;
    private List<String> userIdList;


}
