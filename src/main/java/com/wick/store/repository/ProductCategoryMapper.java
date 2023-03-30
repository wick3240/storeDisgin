package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.store.domain.Dto.ProductCategoryDto;
import com.wick.store.domain.entity.ProductCategoryEntity;
import com.wick.store.domain.vo.ProductCategoryListVo;
import com.wick.store.domain.vo.ProductCategoryVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProductCategoryMapper extends BaseMapper<ProductCategoryEntity> {
    /**
     * 分页查询分类表
     * @param page
     * @param productCategoryDto
     * @return
     */
    Page<ProductCategoryVo> queryPage(Page<ProductCategoryVo> page, ProductCategoryDto productCategoryDto);

    /**
     * 根据id删除分类
     * @param id
     */
    void deleteByCid(String id);

    /**
     * 根据分类找公式
     * @param cid
     * @return
     */
    String selectByWorkflow(String cid);

    /**
     * 找cidList，用于展示
     * @return
     */
    List<ProductCategoryListVo> selectByCidList();
}
