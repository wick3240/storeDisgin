package com.wick.store.Controller;

import com.wick.store.domain.Dto.ProductCategoryDto;
import com.wick.store.service.ProductCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ProductCategoryControllerTest {
    @Autowired
    private ProductCategoryService productCategoryService;
    @Test
    void queryPage() {
        ProductCategoryDto productCategoryDto=new ProductCategoryDto();
        productCategoryService.queryPage(productCategoryDto);
    }

    @Test
    void save() {
        ProductCategoryDto productCategoryDto=new ProductCategoryDto();
        productCategoryDto.setDescription("穷途末路");
        productCategoryDto.setUrl("www.baidu.com");
        productCategoryDto.setName("policy");
        productCategoryDto.setWorkflowFormula(String.valueOf(UUID.randomUUID()));
        productCategoryDto.setWorkflowId((int) Math.random());
        productCategoryService.save(productCategoryDto);
    }

    @Test
    void delete() {
        productCategoryService.delete("54ff791ed017067ac36f7859d680f7c9");
    }

    @Test
    void update() {

        ProductCategoryDto productCategoryDto=new ProductCategoryDto();
        productCategoryDto.setDescription("i love mysql");
        productCategoryDto.setUrl("www.google.com");
        productCategoryDto.setName("policy");
        productCategoryDto.setWorkflowFormula("77777");
        productCategoryDto.setWorkflowId(2);
        productCategoryDto.setId("919b6270258dd34f6e5582704e7b0b9a");
        productCategoryService.update(productCategoryDto);
    }
    @Test
    void listProductCategory(){
        productCategoryService.listProductCategory();
    }
}