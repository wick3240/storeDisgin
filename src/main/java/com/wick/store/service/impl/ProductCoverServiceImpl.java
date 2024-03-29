package com.wick.store.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wick.store.domain.Dto.ProductCoverDto;
import com.wick.store.domain.entity.ProductCoverEntity;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.ProductCoverVo;
import com.wick.store.repository.ProductCoverMapper;
import com.wick.store.service.ProductCoverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;
@Service
@Slf4j
public class ProductCoverServiceImpl extends ServiceImpl<ProductCoverMapper, ProductCoverEntity>
        implements ProductCoverService {
    @Autowired
    private ProductCoverMapper productCoverMapper;
    @Override
    public ProductCoverVo save(ProductCoverDto productCoverDto) {
        ProductCoverEntity productCoverEntity=new ProductCoverEntity();
        BeanUtils.copyProperties(productCoverDto,productCoverEntity);
        productCoverEntity.setStatus(1);
        this.save(productCoverEntity);
        ProductCoverVo productCoverVo=new ProductCoverVo();
        BeanUtils.copyProperties(productCoverDto,productCoverVo);
        return productCoverVo;

    }

    @Override
    public void update(ProductCoverDto productCoverDto) {
        ProductCoverEntity productCoverEntity=new ProductCoverEntity();
        BeanUtils.copyProperties(productCoverDto, productCoverEntity);
        this.updateById(productCoverEntity);
    }

    @Override
    public PageVO<ProductCoverVo> queryPage(ProductCoverDto productCoverDto) {
        PageVO<ProductCoverVo> data=new PageVO();
        int current = productCoverDto.getPage() == null ? 1 : productCoverDto.getPage();
        int size = productCoverDto.getRow() == null ? 5 : productCoverDto.getRow();
        Page<ProductCoverVo> page=new Page<>(current,size);
        Page<ProductCoverVo> pages=productCoverMapper.queryPage(page,productCoverDto);
        data.setList(pages.getRecords());
        data.setPage(pages.getCurrent());
        data.setPageSize(pages.getSize());
        data.setTotal(pages.getTotal());
        return data;
    }

    @Override
    public void delete(String id) {
        ProductCoverEntity productCoverEntity = new ProductCoverEntity();
        log.info("id===========>"+id);
        //productCoverEntity.setIsDeleted(true);
//        UpdateWrapper<ProductCoverEntity> updateWrapper=new UpdateWrapper<>();
//        updateWrapper.eq("id",id);
        update(productCoverEntity, new UpdateWrapper<ProductCoverEntity>().set("is_deleted",1).eq("id", id));
    //ids.forEach(id -> update(productCoverEntity, new UpdateWrapper<ProductCoverEntity>().eq("id", id)));
    }
}
