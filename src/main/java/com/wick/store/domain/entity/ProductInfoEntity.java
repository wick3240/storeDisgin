package com.wick.store.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("product_info")
public class ProductInfoEntity extends BaseEntity implements Serializable {
    /** 产品名字 */
    private String name;
    /** 描述 */
    private String description;
    /** 分类id */
    private String cid;
    /** 产品状态 */
    private Integer status;
    /** 用于轮播图的排序 */
    private Integer sort;
    /** 产品单号 */
    private String pubCode;
    /** 产品图 */
    private String coverUrl;
    /**
     * 权重，用于热门推荐
     */
    private Integer weight;
}
