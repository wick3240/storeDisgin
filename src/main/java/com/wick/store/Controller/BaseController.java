package com.wick.store.Controller;

import com.wick.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.rmi.ServerException;

public class BaseController {
    private static final int OK=200;
    @ExceptionHandler(ServerException.class)
    public JsonResult handleException(Throwable e){
        return new JsonResult();
    }
}
