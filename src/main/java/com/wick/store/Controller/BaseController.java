package com.wick.store.Controller;

import com.wick.store.service.ex.InsertException;
import com.wick.store.service.ex.PasswordNotMatchException;
import com.wick.store.service.ex.UserNameDuplicatedException;
import com.wick.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.rmi.ServerException;
public class BaseController {
    public static final int OK=200;
    @ExceptionHandler(ServerException.class)
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result=new JsonResult<>(e);
        if (e instanceof UserNameDuplicatedException){
            result.setState(4000);
            result.setMessage("用户已经被注册了");
        }
        else if(e instanceof InsertException){
            result.setState(5000);
            result.setMessage("注册发生异常");
        } else if (e instanceof UserNameDuplicatedException) {
            result.setState(4001);
            result.setMessage("用户数据不存在的异常");

        }
        else if(e instanceof PasswordNotMatchException){
            result.setState(4002);
            result.setMessage("用户密码错误异常");
        }
        return result;
    }
}
