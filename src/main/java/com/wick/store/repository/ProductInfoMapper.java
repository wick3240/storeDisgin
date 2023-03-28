package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.store.domain.Dto.ProductInfoDto;
import com.wick.store.domain.entiey.ProductInfoEntity;
import com.wick.store.domain.vo.ProductCategoryListVo;
import com.wick.store.domain.vo.ProductCategoryVo;
import com.wick.store.domain.vo.ProductHotVo;
import com.wick.store.domain.vo.ProductInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProductInfoMapper extends BaseMapper<ProductInfoEntity> {
    List<ProductHotVo> queryHotProduct(String cid);


    ProductInfoVo selectByProduct(String productId);

    Page<ProductInfoVo> queryPage(Page<ProductInfoVo> page, ProductInfoDto productInfoDto);

    void deleteRemovedProduct(String id);

    void updateByStatus(String productId);



}
