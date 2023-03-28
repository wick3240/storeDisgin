package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wick.store.domain.entiey.PublishApproveEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface PublishApproveMapper extends BaseMapper<PublishApproveEntity> {
}
