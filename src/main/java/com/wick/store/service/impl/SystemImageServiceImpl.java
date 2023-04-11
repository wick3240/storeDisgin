package com.wick.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wick.store.domain.entity.SystemImageEntity;
import com.wick.store.repository.SystemImageMapper;
import com.wick.store.service.SystemImageService;
import com.wick.store.service.ex.fileEx.FileTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class SystemImageServiceImpl extends ServiceImpl<SystemImageMapper,SystemImageEntity>
        implements SystemImageService {
    private String fileimg = "src/main/resources/static/images";
    private String UREIMG = "http://localhost:8080/";
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    @Autowired
    private SystemImageMapper systemImageMapper;

    @Override
    public String saveFileData(MultipartFile file) throws IOException {
        //我们简单验证一下file文件是否为空
        if (file.equals("")) {
            return null;
        }
        Date date = new Date();
        //获取当前系统时间年月这里获取到月如果要精确到日修改("yyyy-MM-dd")
        String dateForm = new SimpleDateFormat("yyyy-MM").format(date);
        String casePath = fileimg + dateForm;
        //获取图片后缀
        String imgFormat = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持");
        }
        //判断文件是否存在
        File f = new File(casePath);
        try {
            if (!f.exists()) {
                f.mkdirs();
            }
        } catch (Exception e) {
            return null;
        }
        //给图片重新随机生成名字
        String name = UUID.randomUUID()+ imgFormat;
        //保存图片
        file.transferTo(new File(casePath  + name));
        //拼接要保存在数据中的图片地址
        //dateForm 这是动态的文件夹所以要和地址一起存入数据库中
        String urlImg = UREIMG + "show?fileUrl=" + dateForm + "/" + name;
        //放入对应的字段中
        return urlImg;
    }
}


