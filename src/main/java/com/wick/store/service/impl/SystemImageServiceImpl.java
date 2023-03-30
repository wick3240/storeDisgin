package com.wick.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wick.store.domain.Dto.ProductCoverDto;
import com.wick.store.domain.entity.SystemImageEntity;
import com.wick.store.domain.vo.PageVO;
import com.wick.store.repository.SystemImageMapper;
import com.wick.store.service.SystemImageService;
import com.wick.store.util.PicUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
public class SystemImageServiceImpl extends ServiceImpl<SystemImageMapper,SystemImageEntity>
        implements SystemImageService {
    @Autowired
    private SystemImageMapper systemImageMapper;
    @Override
    public String saveFileData(MultipartFile file) {
        SystemImageEntity imageEntity = new SystemImageEntity();
        //原文件名
        String originalFilename = file.getOriginalFilename();
        long fileSize = file.getSize();
        String[] strings = originalFilename.split("\\.");
        //文件名字
        String fileName = strings[0];
        String fileType = "txt";
        if(strings.length > 1) {
            fileType = strings[1];
        }
        try {
            log.info("start to upload image file===");
            byte[] image = PicUtils.compressPicForScale(file.getBytes(),300) ;
            imageEntity.setImageData(image);
            imageEntity.setFileType(fileType);
            imageEntity.setFileSize((long)image.length);
            imageEntity.setFileName(fileName);
            imageEntity.setIsCompress(true);
            systemImageMapper.insert(imageEntity);
            log.info("file upload success");
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return imageEntity.getId();
    }


}
