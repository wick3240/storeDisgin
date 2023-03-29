package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.store.domain.Dto.ProductCategoryDto;
import com.wick.store.domain.entity.ProductCategoryEntity;
import com.wick.store.domain.vo.ProductCategoryListVo;
import com.wick.store.domain.vo.ProductCategoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProductCategoryMapper extends BaseMapper<ProductCategoryEntity> {
    Page<ProductCategoryVo> queryPage(Page<ProductCategoryVo> page, ProductCategoryDto productCategoryDto);

    void deleteByCid(String id);

    List<ProductCategoryListVo> selectTop(String cid);

    String selectByWorkflow(String cid);

    List<ProductCategoryListVo> selectByCidList();
}
