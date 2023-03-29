package com.wick.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wick.store.domain.entity.ProductInfoEntity;
import com.wick.store.domain.vo.ProductBannerProductVo;
import com.wick.store.domain.vo.ProductHotVo;
import com.wick.store.repository.ProductInfoMapper;
import com.wick.store.service.ProductBannerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
public class ProductBannerServiceImpl extends ServiceImpl<ProductInfoMapper, ProductInfoEntity>
        implements ProductBannerService {
    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductBannerProductVo> listProductBanner(String productType) {
        log.info("listProductBanner根据产品类型查找轮播图 productType===>{}", productType);
        QueryWrapper<ProductInfoEntity> qw = new QueryWrapper<ProductInfoEntity>().eq("is_deleted", "0").eq("status", "1").orderByAsc("sort");
        List<ProductInfoEntity> productInfoEntities = list(qw);
        return productInfoEntities.stream().map(productInfoEntity -> {
            ProductBannerProductVo productBannerProductVo = new ProductBannerProductVo();
            BeanUtils.copyProperties(productInfoEntity, productBannerProductVo);
            return productBannerProductVo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductHotVo> queryHotProduct(String cid) {
        List<ProductHotVo> productHotVos = productInfoMapper.queryHotProduct(cid);
        return productHotVos;
    }
}

