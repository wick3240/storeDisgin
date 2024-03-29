package com.wick.store.Controller;

import com.wick.store.domain.Dto.ProductCoverDto;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.service.SystemImageService;
import com.wick.store.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@Api(tags = "图片上传服务")
@RestController
@RequestMapping("/upload")
public class SystemImageFileController {
    @Autowired
    private SystemImageService systemImageService;


    @ApiOperation(value = "上传文件数据", notes = "上传文件数据")
    @PostMapping("/data")
    public JsonResult saveFileData(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        return new JsonResult( systemImageService.saveFileData(file,request));
    }



}
