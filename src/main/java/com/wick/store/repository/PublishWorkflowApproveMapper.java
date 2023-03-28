package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.store.domain.Dto.PublishApproveDto;
import com.wick.store.domain.entiey.PublishApproveEntity;
import com.wick.store.domain.entiey.PublishWorkflowApproveEntity;
import com.wick.store.domain.vo.ProductPublishApproveVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper
@Component
public interface PublishWorkflowApproveMapper extends BaseMapper<PublishWorkflowApproveEntity> {



}
