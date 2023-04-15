package com.wick.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wick.store.domain.Dto.CreateSubscribeRecordDto;
import com.wick.store.domain.Dto.SubscribeRecordRequstDto;
import com.wick.store.domain.Dto.SubscribeWorkflowApproveDto;
import com.wick.store.domain.entity.SubscribeApproveEntity;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.SubscribeRecordAndProductVO;

import java.util.List;

public interface SubscribeRecordService extends IService<SubscribeApproveEntity> {
    /**
     * 批量订阅
     * @param createSubscribeRecordDto
     */
    void batchSave(CreateSubscribeRecordDto createSubscribeRecordDto);

    /**
     * 分页查询订阅者订阅信息
     * @param recordRequstDto
     * @return
     */
    PageVO<SubscribeRecordAndProductVO> selectPageBySubscribeUser(SubscribeRecordRequstDto recordRequstDto);

    /**
     * 分类查询审批者审批信息
     * @param recordRequstDto
     * @return
     */

    PageVO<SubscribeRecordAndProductVO> selectApprovePageData(SubscribeRecordRequstDto recordRequstDto);

    /**
     *流程审批更新
     * @param subscribeWorkflowApproveDto
     */

    void workflowUpdateApprove(SubscribeWorkflowApproveDto subscribeWorkflowApproveDto);
}
