package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.store.domain.Dto.ProductInfoDto;
import com.wick.store.domain.entity.ProductInfoEntity;
import com.wick.store.domain.vo.ProductHotVo;
import com.wick.store.domain.vo.ProductInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper
@Component
public interface ProductInfoMapper extends BaseMapper<ProductInfoEntity> {
    List<ProductHotVo> queryHotProduct(String cid);


    ProductInfoVo selectByProduct(String productId);

    Page<ProductInfoVo> queryPage(Page<ProductInfoVo> page, ProductInfoDto productInfoDto);

    void deleteRemovedProduct(String id);

    void updateByStatus(String productId, Integer status);


    void updateBatchProductStatus(@Param("productIds") List<String> productIds,@Param("status") Integer status);

    /**
     * 查找单号对应的产品id
     * @param pubCodeSet
     * @return
     */
    List<String> getProductIdsByPubCode(Set<String> pubCodeSet);
}
