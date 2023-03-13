package com.wick.store.service;

import java.util.List;

public interface ProductBannerService {
    List<ProductBannerVo> listProductBanner(String productType);

    List<ProductHotVo> queryHotProduct(String productType, String cid);
}
