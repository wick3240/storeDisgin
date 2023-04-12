package com.wick.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wick.store.domain.entity.SystemImageEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface SystemImageService extends IService<SystemImageEntity> {
    /**
     * 保存图片信息
     *
     * @param file
     */
    String saveFileData(MultipartFile file, HttpServletRequest request) throws IOException;



}
