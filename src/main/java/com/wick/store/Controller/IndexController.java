package com.wick.store.Controller;


import com.wick.store.domain.vo.ProductBannerProductVo;
import com.wick.store.domain.vo.ProductCategoryListVo;
import com.wick.store.domain.vo.ProductHotVo;
import com.wick.store.service.ProductBannerService;
import com.wick.store.service.ProductCategoryService;
import com.wick.store.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags = "首页模块")
@RestController
@RequestMapping("api/Index")
public class IndexController {
    @Autowired
    private ProductBannerService productBannerService;

    @Autowired
    private ProductCategoryService productCategoryService;


    @ApiOperation(value = "轮播图展示", notes = "轮播图展示")
    @GetMapping("/banner/list")
    public JsonResult<List<ProductBannerProductVo>> listProductBanner() {
        return new JsonResult<List<ProductBannerProductVo>>(productBannerService.listProductBanner());
    }
    @ApiOperation(value = "热门产品推荐（按订阅数）", notes = "热门产品推荐（按订阅数）")
    @GetMapping("/queryHotProduct")
    public JsonResult<List<ProductHotVo>> queryHotProduct(String cid) {
        return new JsonResult<>(productBannerService.queryHotProduct(cid));
    }

    @ApiOperation(value = "产品分类", notes = "按产品类型分类")
    @GetMapping("/productCategory/list")
    public JsonResult<List<ProductCategoryListVo>> listProductCategory() {
        return new JsonResult<>(productCategoryService.listProductCategory());
    }

}
