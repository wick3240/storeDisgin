package com.wick.store.Controller;

import com.wick.store.service.ex.InsertException;
import com.wick.store.service.ex.PasswordNotMatchException;
import com.wick.store.service.ex.UpdateException;
import com.wick.store.service.ex.UserNameDuplicatedException;
import com.wick.store.service.ex.fileEx.*;
import com.wick.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;


import javax.servlet.http.HttpSession;
import java.rmi.ServerException;
public class BaseController {

    @ExceptionHandler({ServerException.class,FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result=new JsonResult<>(e);
        if (e instanceof UserNameDuplicatedException){
            result.setCode(4000);
            result.setMsg("用户已经被注册了");
        }
        else if(e instanceof InsertException){
            result.setCode(5000);
            result.setMsg("注册发生异常");
        } else if (e instanceof UserNameDuplicatedException) {
            result.setCode(4001);
            result.setMsg("用户数据不存在的异常");

        }
        else if(e instanceof PasswordNotMatchException){
            result.setCode(4002);
            result.setMsg("用户密码错误异常");
        } else if (e instanceof UpdateException) {
            result.setCode(5001);
            result.setMsg("更新数据是产生未知异常");

        }
        else if (e instanceof FileEmptyException) {
            result.setCode(6000);
        } else if (e instanceof FileSizeException) {
            result.setCode(6001);
        } else if (e instanceof FileTypeException) {
            result.setCode(6002);
        } else if (e instanceof FileStateException) {
            result.setCode(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setCode(6004);
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
