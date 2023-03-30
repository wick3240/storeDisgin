package com.wick.store.Controller;

import com.wick.store.domain.entity.AuthResponse;
import com.wick.store.domain.entity.LoginForm;
import com.wick.store.domain.entity.UserEntity;
import com.wick.store.repository.UserMapper;
import com.wick.store.service.UserService;
import com.wick.store.util.JsonResult;
import com.wick.store.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("用户身份验证")
@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @PostMapping("/login")
    public JsonResult login(@RequestBody LoginForm loginForm) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginForm.getUsername(),
                        loginForm.getPassword()
                )
        );

        //生成JWT
        final String token = jwtTokenUtil.createToken(authentication);

        //从数据库中查找用户信息
        UserEntity user = userService.findByUid(loginForm.getUsername());

        //返回JWT和用户信息
        return new JsonResult(new AuthResponse(token,user));
    }
}
