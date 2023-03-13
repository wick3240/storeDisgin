package com.wick.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wick.store.service.ProductBannerService;
import com.wick.store.util.DictUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
@Slf4j
@Service
public class ProductBannerServiceImpl extends ServiceImpl<>
        implements ProductBannerService {
    @Override
    public List<ProductBannerVo> listProductBanner(String productType) {
        log.info("listProductBanner根据产品类型查找轮播图 productType===>{}", productType);
        QueryWrapper<ProductBannerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", "0").eq("status", "1").eq("product_type", productType).orderByAsc("sort");
        QueryWrapper<ProductBannerEntity> qw = new QueryWrapper<ProductBannerEntity>().eq("is_deleted", "0").eq("status", "1").orderByAsc("sort");
        List<ProductBannerEntity> productBannerEntities = new ArrayList<>();
        if (StringUtils.isNotBlank(productType)){
            productBannerEntities = list(queryWrapper);
        }else {
            productBannerEntities = list(qw);
        }
        return productBannerEntities.stream().map(productBannerEntity -> {
            ProductBannerVo productBannerVo = new ProductBannerVo();
            BeanUtils.copyProperties(productBannerEntity, productBannerVo);
            return productBannerVo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductHotVo> queryHotProduct(String productType, String cid) {
        List<String> cids = StringUtils.isBlank(cid) ? new ArrayList<>() : new ArrayList<>(Arrays.asList(cid.split(",", -1)));
        List<ProductHotVo> productHotVos = productInfoMapper.queryHotProduct(productType, cids);
        Map<String, String> productTypeDict = DictUtils.getProductTypeDict();
        List<DictVo> productTypeDictVo = DictUtils.getProductTypeDictVo();
        Map<String, String> productTypeImageDict = productTypeDictVo.stream().collect(HashMap::new, (m, v) -> m.put(v.getValue(), v.getImageUrl()), HashMap::putAll);
        productHotVos.forEach(p -> {
            //Long count = productInfoMapper.getSubscribeCount(p.getId());
            //p.setSubscribeCount(count);
            p.setProductTypeLabel(productTypeDict.get(p.getProductType()));
            p.setProductTypeImageUrl(productTypeImageDict.get(p.getProductType()));
        });
        return productHotVos;
    }
}

