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
        List<CreateSubscribeRecordDto> createSubscribeRecordDtoList= productInfoMapper.selectBySubscriber(pubCode);
        System.out.println(createSubscribeRecordDtoList);
        subscribeRecordService.batchSave(createSubscribeRecordDtoList);
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
        subscribeWorkflowApproveDto.setNodeApprover("b4dc7f77955968db5b953c95f89afd7b");
        subscribeWorkflowApproveDto.setWorkflowId(0);
        subscribeWorkflowApproveDto.setNodeApproveStatus(1);
        subscribeWorkflowApproveDto.setSubscribeApproveNodes(subscribeWorkflowApproveMapper.selectNode("d90fffa43ffc7bd348ea3b2bfc33bfb8"));
        subscribeRecordService.workflowUpdateApprove(subscribeWorkflowApproveDto);
    }
}