package com.wick.store.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wick.store.domain.Dto.ProductCategoryDto;
import com.wick.store.domain.entity.ProductCategoryEntity;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.ProductCategoryListVo;
import com.wick.store.domain.vo.ProductCategoryVo;
import com.wick.store.repository.ProductCategoryMapper;
import com.wick.store.repository.ProductInfoMapper;
import com.wick.store.service.ProductCategoryService;
import com.wick.store.service.ex.CategoryNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ProductCategoryImpl extends ServiceImpl<ProductCategoryMapper,ProductCategoryEntity>
        implements ProductCategoryService {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public PageVO<ProductCategoryVo> queryPage(ProductCategoryDto productCategoryDto) {
        PageVO<ProductCategoryVo> data=new PageVO();
        int current = productCategoryDto.getPage() == null ? 1 : productCategoryDto.getPage();
        int size = productCategoryDto.getRow() == null ? 5 : productCategoryDto.getRow();
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
        BeanUtils.copyProperties(productCategoryDto,productCategoryVo);
        return productCategoryVo;
    }

    @Override
    public void delete(String id) {
        log.info("删除对应分类：id=====>",id);
        ProductCategoryEntity productCategoryEntity=getOne(new QueryWrapper<ProductCategoryEntity>()
                .eq("id",id).eq("is_deleted",0));
        if (ObjectUtil.isEmpty(productCategoryEntity)){
            throw new CategoryNotFoundException("找不到对应的分类数据");
        }
        productCategoryMapper.deleteByCid(productCategoryEntity.getId());

    }

    @Override
    public void update(ProductCategoryDto productCategoryDto) {
        ProductCategoryEntity productCategoryEntity=getOne(new QueryWrapper<ProductCategoryEntity>()
                .eq("id",productCategoryDto.getId()).eq("is_deleted",0));
        if (ObjectUtil.isEmpty(productCategoryEntity)){
            throw new CategoryNotFoundException("找不到对应的分类数据");
        }
        BeanUtils.copyProperties(productCategoryDto, productCategoryEntity);
        this.updateById(productCategoryEntity);
    }

    @Override
    public List<ProductCategoryListVo> listProductCategory() {
        List<ProductCategoryListVo> productCategoryListVos =productCategoryMapper.selectByCidList();
        return productCategoryListVos;
    }


}
