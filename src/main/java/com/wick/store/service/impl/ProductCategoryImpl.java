package com.wick.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.store.domain.Dto.ProductCategoryDto;
import com.wick.store.domain.entiey.ProductCategoryEntity;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.ProductCategoryVo;
import com.wick.store.repository.ProductCategoryMapper;
import com.wick.store.service.ProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import static com.baomidou.mybatisplus.core.toolkit.Wrappers.update;

public class ProductCategoryImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public PageVO<ProductCategoryVo> queryPage(ProductCategoryDto productCategoryDto) {
        PageVO<ProductCategoryVo> data=new PageVO();
        int current = productCategoryDto.getPage() == null ? 1 : productCategoryDto.getPage();
        int size = productCategoryDto.getRow() == null ? 10 : productCategoryDto.getRow();
        Page<ProductCategoryVo> page=new Page<>(current,size);
        Page<ProductCategoryVo> pages=productCategoryMapper.queryPage(page,productCategoryDto);
        data.setList(pages.getRecords());
        data.setPage(pages.getCurrent());
        data.setPageSize(pages.getSize());
        data.setTotal(pages.getTotal());
        return data;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductCategoryVo save(ProductCategoryDto productCategoryDto) {
        ProductCategoryEntity productCategoryEntity=new ProductCategoryEntity();
        BeanUtils.copyProperties(productCategoryDto,productCategoryEntity);
        productCategoryMapper.insert(productCategoryEntity);
        ProductCategoryVo productCategoryVo=new ProductCategoryVo();
        BeanUtils.copyProperties(productCategoryVo,productCategoryDto);
        return productCategoryVo;
    }

    @Override
    public void delete(List<String> ids) {
       ProductCategoryEntity productCategoryEntity=new ProductCategoryEntity();

    }

    @Override
    public void update(ProductCategoryDto productCategoryDto) {

    }


}
