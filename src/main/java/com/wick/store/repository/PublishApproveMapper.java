package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.store.domain.Dto.PublishApproveDto;
import com.wick.store.domain.entity.PublishApproveEntity;
import com.wick.store.domain.vo.ProductPublishApproveVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface PublishApproveMapper extends BaseMapper<PublishApproveEntity> {
    Page<ProductPublishApproveVo> selectPublisherApproveNodeData(Page<ProductPublishApproveVo> page, String approver, List<Integer> statusList, List<Integer> nodeStatusList, PublishApproveDto requestDto);

    void updateByApproveStatus(Integer approveStatus, String approveUser, Date approveTime, String pubCode);

}
