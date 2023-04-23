package com.wick.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wick.store.domain.Dto.ProductCoverDto;
import com.wick.store.domain.entity.ProductCoverEntity;

import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.ProductCoverVo;

import java.util.List;

public interface ProductCoverService extends IService<ProductCoverEntity> {


    /**
     * 保存产品封面信息
     * @param productCoverDto
     */
    void save(ProductCoverDto productCoverDto);

    /**
     * 修改产品信息
     * @param productCoverDto
     */
    void update(ProductCoverDto productCoverDto);

    /**
     * 分页查询
     * @param productCoverDto
     * @return
     */
    PageVO<ProductCoverVo> queryPage(ProductCoverDto productCoverDto);

    /**
     * 批量删除
     * @param id
     */
    void delete(String id);
}
