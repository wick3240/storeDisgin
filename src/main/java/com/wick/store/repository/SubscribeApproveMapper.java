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
    Page<SubscribeRecordAndProductVO> selectPageData(Page<SubscribeRecordVO> page, @Param("param") SubscribeRecordRequstDto param);

    Page<SubscribeRecordAndProductVO> selectApproveNodePageData(Page<SubscribeRecordVO> page, String approver, List<Integer> approveStatusList, List<Integer> nodeStatusList, SubscribeRecordRequstDto requstDto);

    void updateByApproveStatus(Integer approveStatus, String subscribeCode, Date approvalTime, String approveUser);
}
