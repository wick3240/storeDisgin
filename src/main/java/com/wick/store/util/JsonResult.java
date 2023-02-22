package com.wick.store.util;

import com.wick.store.domain.vo.PageVO;
import com.wick.store.domain.vo.ProductCategoryVo;
import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult <E> implements Serializable {
    static final String SUCCESS_MESSAGE = "操作成功";
    static final String FAILURE_MESSAGE = "操作失败";

    private Integer code;

    private E data;

    private String msg;

    public static final JsonResult<?> SUCCESS = success();

    public static final JsonResult<?> FAILURE = failure();

    private static JsonResult<?> success() {
        return new JsonResult<>(JsonResultStatus.OK, SUCCESS_MESSAGE);
    }

    private static JsonResult<?> failure() {
        return new JsonResult<>(JsonResultStatus.FAILTURE, FAILURE_MESSAGE);
    }

    public JsonResult() {
        this.code = JsonResultStatus.OK;
        this.msg = SUCCESS_MESSAGE;
    }

    public JsonResult(E e) {
        this.code = JsonResultStatus.OK;
        this.msg = SUCCESS_MESSAGE;
        this.data = e;
    }

    public JsonResult(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

    public JsonResult(Integer code, E data) {
        this.code = code;
        this.data = data;
    }
    public JsonResult(Throwable e){
        this.msg=e.getMessage();
    }
}
