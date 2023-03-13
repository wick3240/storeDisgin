package com.wick.store.Controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "产品模块")
@RequestMapping("api/productInfo")
public class ProductInfoController {
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/save")
    public BaseResponse<ProductInfoVo> save(@RequestBody @Valid ProductInfoDto productInfoDto) {
        return new BaseResponse<ProductInfoVo>(productInfoService.save(productInfoDto));
    }

    @ApiOperation(value = "根据id获取产品详情", notes = "根据id获取产品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品id", required = true, dataType = "String", paramType = "path"),
    })
    @GetMapping("/get/{productId}")
    public BaseResponse<ProductInfoVo> findById(@PathVariable String productId) {
        return new BaseResponse<ProductInfoVo>(productInfoService.findById(productId));
    }
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/queryPage")
    public BaseResponse<PageVO<ProductInfoVo>> queryPage(ProductInfoDto productInfoDto) {
        return new BaseResponse<PageVO<ProductInfoVo>>(productInfoService.queryPage(productInfoDto));
    }


    @ApiOperation(value = "修改", notes = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品id", required = true, dataType = "String", paramType = "path"),
    })
    @PostMapping("/update/{productId}")
    public BaseResponse update(@PathVariable String productId, @RequestBody @Valid ProductInfoDto productInfoDto) {
        productInfoDto.setId(productId);
        productInfoService.update(productInfoDto);
        return new BaseResponse<ProductInfoVo>();
    }



    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品id", required = true, dataType = "String", paramType = "path"),
    })
    @PostMapping("/delete/{productId}")
    public BaseResponse delete(@PathVariable String productId) {
        productInfoService.delete(productId);
        return new BaseResponse();
    }
}
