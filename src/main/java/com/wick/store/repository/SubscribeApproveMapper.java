package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.store.domain.Dto.SubscribeRecordRequstDto;
import com.wick.store.domain.entiey.ProductInfoEntity;
import com.wick.store.domain.entiey.SubscribeApproveEntity;
import com.wick.store.domain.vo.SubscribeRecordAndProductVO;
import com.wick.store.domain.vo.SubscribeRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface SubscribeApproveMapper extends BaseMapper<SubscribeApproveEntity> {
    Page<SubscribeRecordAndProductVO> selectPageData(Page<SubscribeRecordVO> page, @Param("param") SubscribeRecordRequstDto param);
}
