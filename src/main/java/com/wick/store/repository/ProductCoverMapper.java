package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.store.domain.Dto.ProductCoverDto;
import com.wick.store.domain.entity.ProductCoverEntity;

import com.wick.store.domain.vo.ProductCoverVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProductCoverMapper  extends BaseMapper<ProductCoverEntity> {
    /**
     * 分页查询
     * @param page
     * @param productCoverDto
     * @return
     */

    Page<ProductCoverVo> queryPage(Page page, ProductCoverDto productCoverDto);
}
