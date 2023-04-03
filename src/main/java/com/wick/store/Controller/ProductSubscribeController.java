package com.wick.store.Controller;

import com.wick.store.domain.Dto.CreateSubscribeRecordDto;
import com.wick.store.domain.Dto.SubscribeRecordRequstDto;
import com.wick.store.domain.Dto.SubscribeWorkflowApproveDto;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.SubscribeRecordAndProductVO;
import com.wick.store.service.SubscribeRecordService;
import com.wick.store.util.JsonResult;
import com.wick.store.util.JsonResultStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "产品订阅和订阅审批")
@RequestMapping("/api/subscribe")
public class ProductSubscribeController {
    @Autowired
    private SubscribeRecordService subscribeRecordService;

    @ApiOperation(value = "批量订阅", notes = "批量订阅")
    @PostMapping("/batch/save")
    public JsonResult batchSave(@RequestBody List<CreateSubscribeRecordDto> createSubscribeRecordDtos) {
        subscribeRecordService.batchSave(createSubscribeRecordDtos);
        return new JsonResult(JsonResultStatus.OK);
    }


    @ApiOperation(value = "根据条件分页查询订阅者的产品订阅信息(针对订阅者)", notes = "根据条件分页查询订阅者的产品订阅信息")
    @GetMapping("/page/by/subscribeUser")
    public JsonResult<PageVO<SubscribeRecordAndProductVO>> selectPageBySubscribeUser(SubscribeRecordRequstDto recordRequstDto) {
        JsonResult jsonResult = new JsonResult(JsonResultStatus.OK);
        PageVO<SubscribeRecordAndProductVO> dataList = subscribeRecordService.selectPageBySubscribeUser(recordRequstDto);
        jsonResult.setData(dataList);
        return jsonResult;
    }
    @ApiOperation(value = "根据条件分页查询产品的订阅信息(针对审批者查询)", notes = "根据条件分页查询产品的订阅信息")
    @GetMapping("/page")
    public JsonResult<PageVO<SubscribeRecordAndProductVO>> selectApprovePageData(SubscribeRecordRequstDto recordRequstDto) {
        JsonResult jsonResult = new JsonResult(JsonResultStatus.OK);
        PageVO<SubscribeRecordAndProductVO> dataList = subscribeRecordService.selectApprovePageData(recordRequstDto);
        jsonResult.setData(dataList);
        return jsonResult;
    }
    @ApiOperation(value = "流程审批",notes = "流程审批")
    @PutMapping("/workflow/update")
    public JsonResult workflowUpdateApprove( @RequestBody SubscribeWorkflowApproveDto subscribeWorkflowApproveDto){
        subscribeRecordService.workflowUpdateApprove(subscribeWorkflowApproveDto);
        return new JsonResult();
    }





}
