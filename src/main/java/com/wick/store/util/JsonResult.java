package com.wick.store.util;

import java.io.Serializable;

public class JsonResult <E> implements Serializable {
    //状态码
    private Integer status;
    //返回数据，因为返回的数据没有固定格式，使用泛型
    private E data;
    //描述信息
    private String message;

    public JsonResult(){}

    public JsonResult(Integer status, E data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
    public JsonResult(Throwable e){
        this.message=e.getMessage();
    }
}
