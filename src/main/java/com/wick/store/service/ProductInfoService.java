package com.wick.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wick.store.domain.Dto.ProductInfoDto;
import com.wick.store.domain.entity.ProductInfoEntity;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService  extends IService<ProductInfoEntity> {
    /**
     * 新增
     *
     * @param productInfoDto
     * @return
     */
    ProductInfoVo save(ProductInfoDto productInfoDto);
    /**
     * 修改
     *
     * @param productInfoDto
     */
    void update(ProductInfoDto productInfoDto);


    /**
     * 分页查询（根据分类）
     *
     * @param productInfoDto
     * @return
     */
    PageVO<ProductInfoVo> queryPage(ProductInfoDto productInfoDto);
    /**
     * 根据id查询
     *
     * @param productId
     * @return
     */
    ProductInfoVo findById(String productId);

    /**
     * 产品删除
     *
     * @param productId 产品id
     */
    void delete(String productId);

    /**
     * 产品下架操作
     * @param productId
     */
    void updateByStatus(String productId);

    /**
     * 批量发布(针对发布者)
     *
     * @param productInfoDto
     */
    void batchPublish( ProductInfoDto productInfoDto);

    String findByApi(String productId);
}
