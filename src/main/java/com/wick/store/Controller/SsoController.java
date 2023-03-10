package com.wick.store.Controller;


import com.wick.store.domain.vo.*;
import com.wick.store.eums.AccountType;
import com.wick.store.service.SsoService;
import com.wick.store.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.xml.ws.soap.Addressing;
import java.io.IOException;

@RestController
@Slf4j
@Api("sso登录校验")
@RequestMapping("sso/v1")
public class SsoController {
    @Autowired
    private SsoService ssoService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "Login")
    @GetMapping("/login")
    public ResponseEntity login(
            @RequestParam(name = "login_type") AccountType loginType,
            @RequestParam(name = "redirect_uri", required = false) String redirectUri,
            HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return ssoService.login(new LoginInfoVo(loginType, redirectUri, null), request, response);
    }


    @ApiOperation(value = "Validate Code")
    @PostMapping("/code/validate")
    public ResponseEntity<AccessTokenResponseVo> validateCode(@Validated @RequestBody ValidateCodeRequestVo vo) {
        HttpHeaders headers = getNoCacheHeaders();
        return ResponseEntity.ok().headers(headers).body(ssoService.createToken(vo));
    }

    @ApiOperation(value = "Refresh Access Token")
    @PostMapping("/access-token/refresh")
    public ResponseEntity<AccessTokenResponseVo> refreshAccessToken(@Validated @RequestBody RefreshAccessTokenRequestVo vo) {
        HttpHeaders headers = getNoCacheHeaders();
        return ResponseEntity.ok().headers(headers).body(ssoService.refreshToken(vo));
    }

    @ApiOperation(value = "Validate Token")
    @PostMapping("/token/validate")
    public ResponseEntity<AccessTokenResponseVo> validateToken(@Validated @RequestBody ValidateTokenRequestVo vo) {
        HttpHeaders headers = getNoCacheHeaders();
        return ResponseEntity.ok().headers(headers).body(ssoService.validateToken(vo));
    }

    @NotNull
    private HttpHeaders getNoCacheHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        return headers;
    }

    @ApiOperation(value = "Get User Info")
    @GetMapping("/get/userInfo")
    public ResponseEntity<SystemUserVo> getUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(ssoService.getUserInfo(request));
    }

    @GetMapping("/user-info/refresh")
    public void refreshUser(@RequestParam AccountType type, @RequestParam String name) {
        userService.removeUser(type,name);
    }

    @ApiOperation(value = "Logout")
    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        ssoService.logout(request, response);
        return ResponseEntity.ok("success");
    }
}
