package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.wick.store.domain.Dto.SubscribeApproveNode;
import com.wick.store.domain.entity.SubscribeWorkflowApproveEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface SubscribeWorkflowApproveMapper extends BaseMapper<SubscribeWorkflowApproveEntity> {
    /**
     * 更新订阅情况
     * @param approveStatus
     * @param approvalTime
     * @param approveUser
     * @param subscribeCode
     * @param nodeId
     * @param cid
     */
    void updateSubscribeStatus(Integer approveStatus, Date approvalTime, String approveUser, String subscribeCode, String nodeId, String cid);

    /**
     * 查找订阅nodelist
     * @param subscribeCode
     * @return
     */

    List<SubscribeWorkflowApproveEntity> selectBySubCodesAndNodeId(String subscribeCode);

    List<SubscribeApproveNode> selectNode(String subId);


}
