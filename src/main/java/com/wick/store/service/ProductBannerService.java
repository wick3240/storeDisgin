package com.wick.store.service;

import com.wick.store.domain.vo.ProductBannerProductVo;
import com.wick.store.domain.vo.ProductCategoryListVo;
import com.wick.store.domain.vo.ProductHotVo;

import java.util.List;

public interface ProductBannerService {
    List<ProductBannerProductVo> listProductBanner();

    List<ProductHotVo> queryHotProduct(String cid);


}
