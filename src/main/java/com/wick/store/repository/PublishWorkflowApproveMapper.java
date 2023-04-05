package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wick.store.domain.Dto.PublishApproveNode;
import com.wick.store.domain.Dto.PublishWorkflowApproveDto;
import com.wick.store.domain.entity.PublishWorkflowApproveEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface PublishWorkflowApproveMapper extends BaseMapper<PublishWorkflowApproveEntity> {

    /**
     * 更新节点中的状态
     * @param approveStatus
     * @param approveUser
     * @param approveTime
     * @param pubCode
     * @param nodeId
     * @param cid
     */
    void updatePublishStatus(Integer approveStatus, String approveUser, Date approveTime, String pubCode, String nodeId, String cid);

    /**
     * 通过单号查userList
     * @param pubCode
     * @return
     */
    List<PublishWorkflowApproveEntity> selectByPubCodeAndUserList(String pubCode);

    /**
     * 查询列表需要的信息，用于测试
     * @param id
     * @return
     */
    List<PublishApproveNode> selectNodeMessage(String id);

    PublishWorkflowApproveEntity selectByCode(String pubCode);
}
