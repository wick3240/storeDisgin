package com.wick.store.service;

import com.wick.store.domain.Dto.ProductCategoryDto;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.ProductCategoryVo;

import java.util.List;

public interface ProductCategoryService {
    PageVO<ProductCategoryVo> queryPage(ProductCategoryDto productCategoryDto);

    ProductCategoryVo save(ProductCategoryDto productCategoryDto);

    void delete(String id);

    void update(ProductCategoryDto productCategoryDto);
}
