package com.wick.store.service.impl;

import com.wick.store.service.ProductBannerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductBannerServiceImplTest {
    @Autowired
    private ProductBannerService productBannerService;

    @Test
    void listProductBanner() {
        productBannerService.listProductBanner();
    }

    @Test
    void queryHotProduct() {
        productBannerService.queryHotProduct("claim");

    }
}