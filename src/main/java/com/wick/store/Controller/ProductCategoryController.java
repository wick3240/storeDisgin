package com.wick.store.Controller;

import com.wick.store.domain.Dto.ProductCategoryDto;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.ProductCategoryVo;
import com.wick.store.service.ProductCategoryService;
import com.wick.store.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api(tags = "系统分类管理")
@RestController
@RequestMapping("/api/productCategory")
public class ProductCategoryController extends BaseController{
    @Autowired
    private ProductCategoryService productCategoryService;

    @ApiOperation(value = "分页管理",notes = "分页管理")
    @GetMapping("/queryPage")
    public JsonResult<PageVO<ProductCategoryVo>> queryPage(ProductCategoryDto productCategoryDto){
        return new JsonResult<PageVO<ProductCategoryVo>>(productCategoryService.queryPage(productCategoryDto));
    }
    @ApiOperation(value = "新增", notes = "新增")
    @PostMapping("/save")
    public JsonResult<ProductCategoryVo> save(@RequestBody @Valid ProductCategoryDto productCategoryDto) {
        return new JsonResult<>(productCategoryService.save(productCategoryDto));
    }
    @ApiOperation(value = "删除分类", notes = "删除分类")
    @PostMapping("/delete/{id}")
    public JsonResult delete(@PathVariable String id) {
        productCategoryService.delete(id);
        return new JsonResult();
    }
    @ApiOperation(value = "修改", notes = "修改")
    @PostMapping("/update/{productCategoryId}")
    public JsonResult<ProductCategoryVo> update(@PathVariable String productCategoryId, @RequestBody ProductCategoryDto productCategoryDto) {
        productCategoryDto.setId(productCategoryId);
        productCategoryService.update(productCategoryDto);
        return new JsonResult();
    }
    @ApiOperation(value = "获取单个分类信息",notes ="获取单个分类信息" )
    @GetMapping("/get/{id}")
    public JsonResult getCidMessage(@PathVariable String id){

        return new JsonResult(productCategoryService.getCidMessage(id));
    }
}
