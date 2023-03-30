package com.wick.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wick.store.domain.Dto.ProductCoverDto;
import com.wick.store.domain.entity.SystemImageEntity;
import com.wick.store.domain.vo.PageVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SystemImageService extends IService<SystemImageEntity> {
    /**
     * 保存图片信息
     * @param file
     * @return
     */
    String saveFileData(MultipartFile file);



}
