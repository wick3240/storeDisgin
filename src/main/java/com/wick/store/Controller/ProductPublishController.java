package com.wick.store.Controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "产品发布和产品审批")
@RestController
@RequestMapping("api/publish")
public class ProductPublishController {
}