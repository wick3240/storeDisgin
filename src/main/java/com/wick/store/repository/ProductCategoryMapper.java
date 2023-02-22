package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.store.domain.Dto.ProductCategoryDto;
import com.wick.store.domain.entiey.ProductCategoryEntity;
import com.wick.store.domain.vo.ProductCategoryVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategoryEntity> {
    Page<ProductCategoryVo> queryPage(Page<ProductCategoryVo> page, ProductCategoryDto productCategoryDto);
}
