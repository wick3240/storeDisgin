package com.wick.store.Controller;

import com.wick.store.service.ex.InsertException;
import com.wick.store.service.ex.PasswordNotMatchException;
import com.wick.store.service.ex.UpdateException;
import com.wick.store.service.ex.UserNameDuplicatedException;
import com.wick.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;


import javax.servlet.http.HttpSession;
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
        } else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("更新数据是产生未知异常");

        }
        return result;
    }
    /**
     * 获取session对象中的uid
     * @param session session对象
     * @return 当前登录用户的uid的值
     */
    public final String getUidFromSession(HttpSession session){
        return session.getAttribute("uid").toString();
    }
    public final String getUserNameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
