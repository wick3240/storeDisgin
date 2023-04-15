package com.wick.store.Controller;

import com.wick.store.domain.Dto.CreateSubscribeRecordDto;
import com.wick.store.domain.Dto.SubscribeWorkflowApproveDto;
import com.wick.store.repository.ProductInfoMapper;
import com.wick.store.repository.SubscribeWorkflowApproveMapper;
import com.wick.store.service.SubscribeRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductSubscribeControllerTest {
    @Autowired
    private SubscribeRecordService subscribeRecordService;
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private SubscribeWorkflowApproveMapper subscribeWorkflowApproveMapper;
    @Test
    void batchSave() {
        String pubCode="FWD202304056";
        CreateSubscribeRecordDto createSubscribeRecordDto=new CreateSubscribeRecordDto();
        List<CreateSubscribeRecordDto> createSubscribeRecordDtoList= productInfoMapper.selectBySubscriber(pubCode);
        System.out.println(createSubscribeRecordDtoList);
        subscribeRecordService.batchSave(createSubscribeRecordDto);
    }

    @Test
    void selectPageBySubscribeUser() {
    }

    @Test
    void selectApprovePageData() {
    }

    @Test
    void workflowUpdateApprove() {
        SubscribeWorkflowApproveDto subscribeWorkflowApproveDto=new SubscribeWorkflowApproveDto();
        subscribeWorkflowApproveDto.setApprovalTime(new Date());
        subscribeWorkflowApproveDto.setNodeApprover("09d18e4fd3d2e7b956748cf9d4008339");
        subscribeWorkflowApproveDto.setNodeApproveStatus(1);
        subscribeWorkflowApproveDto.setSubscribeApproveNodes(subscribeWorkflowApproveMapper.selectNode("ed02aa76ea6c7d9ac7faa8f2729cddc5"));
        subscribeRecordService.workflowUpdateApprove(subscribeWorkflowApproveDto);
    }
}