package com.wick.store.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("system_image")
public class SystemImageEntity extends BaseEntity{
    @ApiModelProperty(value = "图片数据")
    private byte[] imageData;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件名字")
    private String fileName;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;
    private Boolean isCompress;
}
