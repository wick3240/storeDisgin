package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.store.domain.Dto.PublishApproveDto;
import com.wick.store.domain.entity.PublishApproveEntity;
import com.wick.store.domain.vo.ProductPublishApproveVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface PublishApproveMapper extends BaseMapper<PublishApproveEntity> {
    /**
     * 审批者分页查询订阅者的订单
     * @param page
     * @param approver
     * @param statusList
     * @param nodeStatusList
     * @param requestDto
     * @return
     */
    Page<ProductPublishApproveVo> selectPublisherApproveNodeData(Page<ProductPublishApproveVo> page,
                                                                 String approver,
                                                                 @Param("statusList") List<Integer> statusList,
                                                                 @Param("nodeStatusList") List<Integer> nodeStatusList,
                                                                 PublishApproveDto requestDto);

    /**
     * 更新发布者审批表的状态
     * @param approveStatus
     * @param approveUser
     * @param approveTime
     * @param pubCode
     */
    void updateByApproveStatus(Integer approveStatus, String approveUser, Date approveTime, String pubCode);

}
