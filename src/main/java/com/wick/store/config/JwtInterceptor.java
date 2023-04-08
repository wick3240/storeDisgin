package com.wick.store.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.wick.store.domain.entity.UserEntity;
import com.wick.store.util.JwtTokenUtil;
import io.jsonwebtoken.SignatureException;

import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mclt2017
 * @date 2021年06月24日 17:16
 * Jwt拦截器
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        Map<Object, Object> map = new HashMap<>();
        //如果是OPTIONS请求 直接放行
        String method = request.getMethod();
        try {
            if(method.equals("OPTIONS")){
                return  true;
            }
            //从请求中获取令牌
            String jwtToken = request.getHeader("Authorization");
            if(String.valueOf(jwtToken).equals("null")){
                throw new SignatureException("令牌不合法");
            }
            //验证token
            UserEntity user  = JwtTokenUtil.verifyJwtToken(jwtToken);
            //验证成功后,如果令牌有效时间<=5分钟,则签发新的令牌,刷新令牌时间
            if(user != null){
                if(user.getExpireTime() - System.currentTimeMillis() <= 1000L * 60 * 5){
                    JwtTokenUtil.refreshToken(user);
                }
                return  true ;
            }else{
                map.put("success",false);
                map.put("code",401);
                map.put("message","令牌已失效,请重新登录");
            }
        }catch(SignatureException e){
            e.printStackTrace();
            map.put("message","令牌不合法");
            map.put("code",401);
            map.put("success",false);
        }catch (Exception e) {
            e.printStackTrace();
            map.put("message","令牌验签失败:"+e.getMessage());
            map.put("success",false);
        }
        String jsonMap = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(jsonMap);
        return false;
    }
}
