package com.wick.store.service.impl;

import com.wick.store.domain.Dto.ProductInfoDto;
import com.wick.store.domain.vo.ProductInfoVo;
import com.wick.store.service.ProductInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ProductInfoServiceImplTest {
    @Autowired
    private ProductInfoService productInfoService;

    @Test
    void save() {
        ProductInfoDto productInfoDto=new ProductInfoDto();
        productInfoDto.setName("productTest3");
        productInfoDto.setCid("3f6f70b4c342aad8f45a9818d713ce87");
        productInfoDto.setStatus(0);
        productInfoDto.setSort(1);
        productInfoDto.setDescription("howard地锅");
        productInfoService.save(productInfoDto);
    }

    @Test
    void update() {
        ProductInfoDto productInfoDto=new ProductInfoDto();
        productInfoDto.setId("3c38f73b7ea3411a6cf7a75149dae628");
        productInfoDto.setName("wick");
        productInfoDto.setCid("claim");
        productInfoDto.setStatus(1);
        productInfoDto.setSort(1);
        productInfoDto.setPubCode("FWD123456");
        productInfoDto.setDescription("wick地摇");
        productInfoDto.setWeight(1);
        productInfoService.update(productInfoDto);
    }

    @Test
    void queryPage() {
        ProductInfoDto productInfoDto=new ProductInfoDto();
        productInfoService.queryPage(productInfoDto);
    }

    @Test
    void findById() {

      ProductInfoVo productInfoVo= productInfoService.findById("4b58fdd2f78dcb8f888e3706b8a6641e");
        System.out.println(productInfoVo);
    }

    @Test
    void delete() {
        productInfoService.delete("98da54a2ba65261afaf9a0680db9469c");
    }

    @Test
    void updateByStatus() {
        productInfoService.updateByStatus("696abcb85e466315215464da35d22ff2");
    }

    @Test
    void batchPublish() {
    }
}