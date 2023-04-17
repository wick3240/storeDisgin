package com.wick.store.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
    /** 产品状态
     * 未发布是 0
     * 发布中的状态是 1
     * 发布成功是 2
     * 拒绝和下架都是 3*/
    private Integer status;
    /** 用于轮播图的排序 */
    private Integer sort;
    /** 产品单号 */
    @TableField(value = "pub_code")
    private String pubCode;
    /** 产品图 */
    @TableField(value = "cover_url")
    private String coverUrl;
    /**
     * 权重，用于热门推荐
     */
    private Integer weight;
}
