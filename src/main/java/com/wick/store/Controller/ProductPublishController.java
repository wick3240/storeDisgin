package com.wick.store.Controller;

import com.wick.store.service.PublishApproveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(tags = "产品发布和产品审批")
@RestController
@RequestMapping("api/publish")
public class ProductPublishController {
    @Autowired
    private PublishApproveService approveService;

    @ApiOperation(value = "根据条件分页查询产品的发布信息审批列表", notes = "根据条件分页查询产品的发布信息审批列表")
    @GetMapping("/page")
    public BaseResponse<PageVO<ProductPublishApproveVo>> selectPublisherApprovePage(SubscribeRecordRequstDto recordRequestDto) {
        BaseResponse baseResponse = new BaseResponse(BaseResponseStatus.OK);
        PageVO<ProductPublishApproveVo> dataList = approveService.selectPublisherApprovePage(recordRequestDto);
        baseResponse.setData(dataList);
        return baseResponse;
    }
    @ApiOperation(value = "批量审批", notes = "批量审批")
    @PostMapping("/batch/update")
    public BaseResponse batchUpdateApprove(@RequestBody List<ProductPublishApproveDto> publishApproveDtos) {
        BaseResponse baseResponse = new BaseResponse(BaseResponseStatus.OK);
        approveService.batchUpdateApprove(publishApproveDtos);
        return baseResponse;
    }
    @PutMapping("/workflow/publish")
    @ApiOperation("批量审批发布的产品")
    public BaseResponse workflowPublishApprove(@RequestBody PublishWorkflowApproveDTO publishWorkflowApproveDTO) {
        approveService.workflowPublishApprove(publishWorkflowApproveDTO);
        return new BaseResponse();
    }
}
