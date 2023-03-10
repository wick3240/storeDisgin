package com.wick.store.service;

import com.wick.store.domain.vo.LoginInfoVo;
import com.wick.store.domain.vo.SystemUserVo;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface SsoService extends TokenService {
    /**
     * 登陆
     *
     * @param loginInfoVo
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    ResponseEntity login(LoginInfoVo loginInfoVo,
                         HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;

    /**
     * 登出
     *
     * @param request
     * @param response
     */
    void logout(HttpServletRequest request, HttpServletResponse response);

    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    SystemUserVo getUserInfo(HttpServletRequest request);
}
