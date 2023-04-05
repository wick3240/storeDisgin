package com.wick.store.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wick.store.domain.Dto.CreateSubscribeRecordDto;
import com.wick.store.domain.Dto.ProductInfoDto;
import com.wick.store.domain.entity.ProductInfoEntity;
import com.wick.store.domain.vo.ProductHotVo;
import com.wick.store.domain.vo.ProductInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Mapper
@Component
public interface ProductInfoMapper extends BaseMapper<ProductInfoEntity> {
    /**
     * 根据分类查找分类的热度产品（根据订阅量）
     * @param cid
     * @return
     */
    List<ProductHotVo> queryHotProduct(String cid);

    /**
     * 根据产品id找产品
     * @param productId
     * @return
     */
    ProductInfoVo selectByProduct(String productId);

    /**
     * 分页管理，根据分类id来分页
     * @param page
     * @param productInfoDto
     * @return
     */
    Page<ProductInfoVo> queryPage(Page<ProductInfoVo> page, ProductInfoDto productInfoDto);

    /**
     * 删除产品
     * @param id
     */
    void deleteRemovedProduct(String id);

    /**
     * 下架产品
     * @param productId
     * @param status
     */

    void updateByStatus(String productId, Integer status);

    /**
     * 审批拒绝后更新产品状态
     * @param productIds
     * @param status
     */
    void updateBatchProductStatus(@Param("productIds") List<String> productIds,@Param("status") Integer status);

    /**
     * 查找单号对应的产品id
     * @param pubCodeSet
     * @return
     */
    List<String> getProductIdsByPubCode(@Param("pubCodes") Set<String> pubCodeSet);

    /**
     * 测试用到的查询语句
     * @param name
     * @return
     */
    List<ProductInfoDto> getCidAndId(String name);

    /**
     * 测试订阅的查询语句
     * @param pubCode
     * @return
     */
    List<CreateSubscribeRecordDto> selectBySubscriber(String pubCode);
}
