package com.wick.store.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult <E> implements Serializable {
    //状态码
    private Integer state;
    //返回数据，因为返回的数据没有固定格式，使用泛型
    private E data;
    //描述信息
    private String message;

    public JsonResult(){}

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }
    public JsonResult(Throwable e){
        this.message=e.getMessage();
    }
}
