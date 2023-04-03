package com.wick.store.Controller;


import com.wick.store.domain.Dto.ProductCoverDto;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.ProductCoverVo;
import com.wick.store.service.ProductCoverService;
import com.wick.store.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "请求图片服务")
@RestController
@RequestMapping("/api/cover")
public class ProductCoverController {
    @Autowired
    private ProductCoverService productCoverService;
    @ApiOperation(value = "保存产品封面信息", notes = "保存产品封面信息")
    @PostMapping("/save")
    public JsonResult save(
            @RequestBody @Validated ProductCoverDto productCoverDto) {
        productCoverService.save(productCoverDto);
        return new JsonResult();
    }
    @ApiOperation(value = "修改产品封面信息", notes = "修改产品封面信息")
    @PostMapping("/update/{attachmentId}")
    public JsonResult update(
            @PathVariable String attachmentId,
            @RequestBody @Validated ProductCoverDto productCoverDto) {
        productCoverDto.setId(attachmentId);
        productCoverService.update(productCoverDto);
        return new JsonResult();
    }

    @ApiOperation(value = "分页查询", notes = "可以根据产品类型进行筛选")
    @GetMapping("/queryPage")
    public JsonResult<PageVO<ProductCoverVo>> queryPage(ProductCoverDto productCoverDto) {
        return new JsonResult<PageVO<ProductCoverVo>>(productCoverService.queryPage(productCoverDto));
    }

    @ApiOperation(value = "批量删除", notes = "批量删除")
    @PostMapping("/batch/delete")
    public JsonResult delete(@RequestBody List<String> ids) {
        productCoverService.delete(ids);
        return new JsonResult();
    }
}
