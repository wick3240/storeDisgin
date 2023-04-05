package com.wick.store.Controller;

import com.wick.store.domain.Dto.ProductInfoDto;
import com.wick.store.domain.Dto.PublishApproveNode;
import com.wick.store.domain.Dto.PublishWorkflowApproveDto;
import com.wick.store.domain.entity.PublishWorkflowApproveEntity;
import com.wick.store.repository.ProductInfoMapper;
import com.wick.store.repository.PublishWorkflowApproveMapper;
import com.wick.store.service.ProductInfoService;
import com.wick.store.service.PublishApproveService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductPublishControllerTest {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private PublishWorkflowApproveMapper publishWorkflowApproveMapper;
    @Autowired
    private PublishApproveService approveService;

    @Test
    void selectPublisherApprovePage() {
    }

    @Test
    void batchUpdateApprove() {
        List<ProductInfoDto> productInfoDtos=productInfoMapper.getCidAndId("productTest1");
        System.out.println(productInfoDtos);
       // productInfoService.batchPublish(productInfoDtos);
    }

    @Test
    void workflowPublishApprove() {
        PublishWorkflowApproveDto workflowApproveDto=new PublishWorkflowApproveDto();
        workflowApproveDto.setApprovalTime(new Date());
        workflowApproveDto.setCid("3f6f70b4c342aad8f45a9818d713ce87");
        workflowApproveDto.setWorkflowId(0);
        workflowApproveDto.setNodeApprover("b4dc7f77955968db5b953c95f89afd7b");
        workflowApproveDto.setNodeApproveStatus(1);
        List<PublishApproveNode> publishApproveNodes=publishWorkflowApproveMapper.selectNodeMessage("7f89663723f60e45e0f26888d2340ceb");
        workflowApproveDto.setPublishApproveNodes(publishApproveNodes);
        approveService.workflowPublishApprove(workflowApproveDto);
    }

    @Test
    void updateByStatus() {
    }

    @Test
    void  list(){
        String pubCode="FWD202304056";
        List<PublishWorkflowApproveEntity> handledNodeList = publishWorkflowApproveMapper.selectByPubCodeAndUserList(pubCode);
        System.out.println(handledNodeList);

    }
}