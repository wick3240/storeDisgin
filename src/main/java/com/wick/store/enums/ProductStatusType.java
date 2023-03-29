package com.wick.store.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatusType {
    PENDING_APPROVAL(1, "待审核"),
    PUBLISHED(2, "审核通过"),
    UN_APPROVAL(3, "审核不通过"),
    UNPUBLISHED(0, "未发布"),
    UNDERCARRIAGE(4, "已下架");


    private Integer code;
    private String message;



}
