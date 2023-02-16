package com.wick.store.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定义一个拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 检测全局session对象中是否存在uid数据，如果有就放行，如果没有就重定向到登录页面
     * @Param request 请求对象
     * @Param response 响应对象
     * @Param handler 处理器（把uel和controller映射到一块）
     * @return 返回值为ture放行当前请求，反之拦截当前请求
     * @throws Exception
     */
    @Override
    //在DispatchServlet调用所有处理请求的方法前被自动调用执行的方法
    //springboot会自动把请求对象给到request，响应对象给到response，适配器给到handler
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //通过HttpServletRequest对象来获取session对象
        Object obj=request.getSession().getAttribute("uid");
        if (obj==null){

            response.sendRedirect("/web/login.html");
            return false;
        }
        return true;
    }
}
