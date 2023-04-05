package com.wick.store.Controller;

import com.wick.store.domain.Dto.ProductInfoDto;
import com.wick.store.repository.ProductInfoMapper;
import com.wick.store.service.ProductInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductPublishControllerTest {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Test
    void selectPublisherApprovePage() {
    }

    @Test
    void batchUpdateApprove() {
        List<ProductInfoDto> productInfoDtos=productInfoMapper.getCidAndId("productTest1");
        productInfoService.batchPublish(productInfoDtos);
    }

    @Test
    void workflowPublishApprove() {
    }

    @Test
    void updateByStatus() {
    }
}