package com.wick.store.Controller;

import com.wick.store.domain.Dto.ProductInfoDto;
import com.wick.store.domain.Dto.PublishApproveDto;
import com.wick.store.domain.Dto.PublishWorkflowApproveDto;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.ProductPublishApproveVo;
import com.wick.store.service.ProductInfoService;
import com.wick.store.service.PublishApproveService;
import com.wick.store.util.JsonResult;
import com.wick.store.util.JsonResultStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api(tags = "产品发布和产品审批")
@RestController
@RequestMapping("/api/publish")
public class ProductPublishController {
    @Autowired
    private PublishApproveService approveService;
    @Autowired
    private ProductInfoService productInfoService;

    @ApiOperation(value = "根据条件分页查询产品的发布信息审批列表(审批者)", notes = "根据条件分页查询产品的发布信息审批列表")
    @GetMapping("/page")
    public JsonResult<PageVO<ProductPublishApproveVo>> selectPublisherApprovePage(PublishApproveDto requestDto) {
        JsonResult jsonResult = new JsonResult(JsonResultStatus.OK);
        PageVO<ProductPublishApproveVo> dataList = approveService.selectPublisherApprovePage(requestDto);
        jsonResult.setData(dataList);
        return jsonResult;
    }
    @ApiOperation(value = "发布产品（针对发布者）", notes = "发布产品（针对发布者）")
    @PostMapping("/batch/update")
    public JsonResult batchUpdateApprove(@RequestBody ProductInfoDto productInfoDto) {
        JsonResult jsonResult = new JsonResult(JsonResultStatus.OK);
        productInfoService.batchPublish(productInfoDto);
        return jsonResult;
    }
    @PutMapping("/workflow/publish")
    @ApiOperation("批量审批发布的产品（针对审批者）")
    public JsonResult workflowPublishApprove(@RequestBody PublishWorkflowApproveDto publishWorkflowApproveDto) {
        approveService.workflowPublishApprove(publishWorkflowApproveDto);
        return new JsonResult();
    }

    @ApiOperation("下架操作")
    @PostMapping("/updateByStatus/{productId}")
    public JsonResult updateByStatus(@PathVariable String productId){
        productInfoService.updateByStatus(productId);
        return new JsonResult();

    }
}
