package com.wick.store.Controller;


import com.wick.store.domain.Dto.ProductInfoDto;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.ProductInfoVo;
import com.wick.store.service.ProductInfoService;
import com.wick.store.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "产品模块")
@RequestMapping("/api/productInfo")
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;


    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/save")
    public JsonResult<ProductInfoVo> save(@RequestBody @Valid ProductInfoDto productInfoDto) {
        return new JsonResult<ProductInfoVo>(productInfoService.save(productInfoDto));
    }

    @ApiOperation(value = "根据id获取产品详情", notes = "根据id获取产品详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品id", required = true, dataType = "String", paramType = "path"),
    })
    @GetMapping("/get/{productId}")
    public JsonResult<ProductInfoVo> findById(@PathVariable String productId) {
        return new JsonResult<ProductInfoVo>(productInfoService.findById(productId));
    }
    @ApiOperation(value = "分页查询（点击分类后跳转对应分类的产品）", notes = "分页查询")
    @GetMapping("/queryPage")
    public JsonResult<PageVO<ProductInfoVo>> queryPage(ProductInfoDto productInfoDto) {
        return new JsonResult<PageVO<ProductInfoVo>>(productInfoService.queryPage(productInfoDto));
    }


    @ApiOperation(value = "修改", notes = "修改")
    @PostMapping("/update/{productId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品id", required = true, dataType = "String", paramType = "path"),
    })
    public JsonResult update(@PathVariable String productId, @RequestBody @Valid ProductInfoDto productInfoDto) {
        productInfoDto.setId(productId);
        productInfoService.update(productInfoDto);
        return new JsonResult<ProductInfoVo>();
    }



    @ApiOperation(value = "删除", notes = "删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品id", required = true, dataType = "String", paramType = "path"),
    })
    @PostMapping("/delete/{productId}")
    public JsonResult delete(@PathVariable String productId) {
        productInfoService.delete(productId);
        return new JsonResult();
    }
    @GetMapping("/apikey")
    public String getKey(){
        return "PMAK-64392fd0465bc800393b907f-14ea2bebb04e759ae6308023c38e5c5217";
    }
}
