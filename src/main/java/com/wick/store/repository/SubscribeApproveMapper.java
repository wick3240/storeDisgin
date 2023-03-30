package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.store.domain.Dto.SubscribeRecordRequstDto;
import com.wick.store.domain.entity.SubscribeApproveEntity;
import com.wick.store.domain.vo.SubscribeRecordAndProductVO;
import com.wick.store.domain.vo.SubscribeRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface SubscribeApproveMapper extends BaseMapper<SubscribeApproveEntity> {
    /**
     * 订阅者分页查询订阅者的单号
     * @param page
     * @param param
     * @return
     */
    Page<SubscribeRecordAndProductVO> selectPageData(Page<SubscribeRecordVO> page, @Param("param") SubscribeRecordRequstDto param);

    /**
     * 审批者分页查询订单
     * @param page
     * @param approver
     * @param approveStatusList
     * @param nodeStatusList
     * @param requstDto
     * @return
     */

    Page<SubscribeRecordAndProductVO> selectApproveNodePageData(Page<SubscribeRecordVO> page, String approver, List<Integer> approveStatusList, List<Integer> nodeStatusList, SubscribeRecordRequstDto requstDto);

    /**
     * 更新订阅审批表的状态
     * @param approveStatus
     * @param subscribeCode
     * @param approvalTime
     * @param approveUser
     */
    void updateByApproveStatus(Integer approveStatus, String subscribeCode, Date approvalTime, String approveUser);
}
