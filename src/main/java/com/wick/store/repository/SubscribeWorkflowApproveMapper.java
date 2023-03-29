package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.wick.store.domain.entity.SubscribeWorkflowApproveEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface SubscribeWorkflowApproveMapper extends BaseMapper<SubscribeWorkflowApproveEntity> {
    void updateSubscribeStatus(Integer approveStatus, Date approvalTime, String approveUser, String subscribeCode, String nodeId, String cid);

    List<SubscribeWorkflowApproveEntity> selectBySubCodesAndNodeId(String subscribeCode);
}
