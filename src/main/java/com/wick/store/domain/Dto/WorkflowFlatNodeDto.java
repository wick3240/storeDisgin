package com.wick.store.domain.Dto;

import lombok.Data;

@Data
public class WorkflowFlatNodeDto {
    private String flatNodeLevel;
    private String nodeId;

    public WorkflowFlatNodeDto(String flatNodeLevel, String nodeId) {
        this.flatNodeLevel = flatNodeLevel;
        this.nodeId = nodeId;
    }

    @Override
    public String toString() {
        return flatNodeLevel + '|' + nodeId ;
    }
}
