package com.wick.store.Controller;


import com.wick.store.service.ProductBannerService;
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


    @ApiOperation(value = "轮播图展示", notes = "轮播图展示")
    @GetMapping("/banner/list")
    public BaseResponse<List<ProductBannerVo>> listProductBanner(@RequestParam String productType) {
        return new BaseResponse<List<ProductBannerVo>>(productBannerService.listProductBanner(productType));
    }
    @ApiOperation(value = "热门产品推荐（按订阅数）", notes = "热门产品推荐（按订阅数）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productType", value = "产品类型", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "cid", value = "产品分类id", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping("/queryHotProduct")
    public BaseResponse<List<ProductHotVo>> queryHotProduct(@RequestParam(required = false) String productType,
                                                            @RequestParam(required = false) String cid,) {
        return new BaseResponse<List<ProductHotVo>>(productBannerService.queryHotProduct(productType, cid));
    }

}
