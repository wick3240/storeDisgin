package com.wick.store.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wick.store.config.MpSsoConfig;
import com.wick.store.domain.entiey.JwtTokenEntity;
import com.wick.store.domain.entiey.ValidateCodeEntity;
import com.wick.store.domain.vo.*;
import com.wick.store.eums.AccountType;
import com.wick.store.exception.AccessTokenUnsuccessfulException;
import com.wick.store.exception.JwtTokenException;
import com.wick.store.service.*;
import com.wick.store.util.CustomUserDetails;
import com.wick.store.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static com.wick.store.eums.AppConstant.*;

@Service
@Slf4j
public class SsoServiceImpl implements SsoService {
    @Autowired
    private MpSsoConfig config;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MessageStoreConversionService conversionService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtTokenEntityService jwtTokenEntityService;
    @Autowired
    private SecurityContextLogoutHandler logoutHandler;
    @Autowired
    private UserService userService;
    @Autowired
    private ValidateCodeEntityService validateCodeEntityService;







    @Override
    public ResponseEntity login(LoginInfoVo loginInfoVo, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        return null;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
            if (StringUtils.isBlank(requestTokenHeader)) {
                throw new JwtTokenException("Token cannot be empty.", null);
            }
            String jwtToken = requestTokenHeader.substring(AUTHORIZATION_HEADER_START_STR_LENGTH);
            Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);
            JwtTokenEntity entity = jwtTokenEntityService.getJwtTokenEntityById(claims.getId());
            //移除token 和 refresh token
            if (entity != null) {
                jwtTokenEntityService.removeJwtToken(claims.getId());
                jwtTokenEntityService.removeRefreshToken(entity.getRefreshToken());
            }
            //清空用户缓存,先清理Agent信息，再清理user 信息
            AccountType accountType = AccountType.valueOf((String) claims.get(JWT_CLAIM_NAME_LOGIN_TYPE));

        } catch (JwtTokenException e) {
            log.warn("logout get claims error", e);
        }
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
    }

    @Override
    public SystemUserVo getUserInfo(HttpServletRequest request) {
        try {
            Claims claims = getClaims(request);
            AccountType accountType = AccountType.valueOf((String) claims.get(JWT_CLAIM_NAME_LOGIN_TYPE));
            return userService.findUser(accountType, claims.getSubject());
        } catch (JwtTokenException e) {
            log.info("JWT Token has expired");
            throw new AccessTokenUnsuccessfulException(
                    new AccessTokenUnsuccessfulResponseVo(AccessTokenUnsuccessfulResponseVo.ErrorCode.invalid_request, e.getMessage()));
        }
    }

    @Override
    public AccessTokenResponseVo createToken(ValidateCodeRequestVo vo) {
        //判断code是否有效
        ValidateCodeEntity codeEntity = validateCodeEntityService.getValidateCodeEntityByCode(vo.getCode());

        if (codeEntity == null) {
            throw new AccessTokenUnsuccessfulException(
                    new AccessTokenUnsuccessfulResponseVo(
                            AccessTokenUnsuccessfulResponseVo.ErrorCode.invalid_request,
                            "Code is invalid or expired."));
        }
        Authentication authentication = (Authentication) conversionService.deserialize(codeEntity.getAuthentication());
        AccessTokenResponseVo token = createToken(codeEntity.getAuthentication(), authentication);
        //清除code
        validateCodeEntityService.remove(vo.getCode());
        return token;
    }

    @Override
    public AccessTokenResponseVo refreshToken(RefreshAccessTokenRequestVo vo) {
        JwtTokenEntity tokenEntity = jwtTokenEntityService.getRefreshToken(vo.getRefreshToken());
        if (tokenEntity == null) {
            throw new AccessTokenUnsuccessfulException(
                    new AccessTokenUnsuccessfulResponseVo(
                            AccessTokenUnsuccessfulResponseVo.ErrorCode.invalid_request,
                            "Refresh token is invalid or expired."));
        }
        AccessTokenResponseVo responseVo;
        try {
            //创建新的refresh token
            Authentication authentication = (Authentication) conversionService.deserialize(tokenEntity.getAuthentication());
            responseVo = this.createToken(tokenEntity.getAuthentication(), authentication);
            //移除旧的refresh token
            jwtTokenEntityService.removeRefreshToken(vo.getRefreshToken());
        } catch (Exception e) {
            log.error("Refresh token error", e);
            throw new AccessTokenUnsuccessfulException(
                    new AccessTokenUnsuccessfulResponseVo(
                            AccessTokenUnsuccessfulResponseVo.ErrorCode.invalid_request,
                            "Refresh token is invalid or expired."));
        }
        return responseVo;
    }

    @Override
    public AccessTokenResponseVo validateToken(ValidateTokenRequestVo vo) {
        long expiresIn = 0;
        try {
            Claims claims = jwtTokenUtil.getAllClaimsFromToken(vo.getToken());
            JwtTokenEntity entity = jwtTokenEntityService.getJwtTokenEntityById(claims.getId());
            if (entity != null) {

                expiresIn = (claims.getExpiration().getTime() - System.currentTimeMillis()) / 1000L;
            }

        } catch (JwtTokenException e) {
            throw new AccessTokenUnsuccessfulException(
                    new AccessTokenUnsuccessfulResponseVo(AccessTokenUnsuccessfulResponseVo.ErrorCode.invalid_request, e.getMessage()));
        }
        return new AccessTokenResponseVo(vo.getToken(), expiresIn, null);
    }

    private AccessTokenResponseVo createToken(byte[] authenticationByte, Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        Map<String, Object> claims = new LinkedHashMap<>();
        claims.put(JWT_CLAIM_NAME_LOGIN_TYPE, userDetails.getLoginInfoVo().getLoginType());
        //根据邮箱获取用户信息并将用户所属的bucode进行jwt加密
        String jwtId = UUID.randomUUID().toString().replace("-", "");
        String accessToken = jwtTokenUtil.generateToken(userDetails, claims, jwtId);
        String refreshToken = UUID.randomUUID().toString().replace("-", "");
        AccessTokenResponseVo tokenResponseVo = new AccessTokenResponseVo(
                accessToken,
                Long.valueOf(config.getJwtTokenValidity()),
                refreshToken);
        JwtTokenEntity jwtTokenEntity = new JwtTokenEntity(
                jwtId,  userDetails.getLoginInfoVo().getLoginType(), userDetails.getUsername(),
                accessToken, refreshToken, authenticationByte);
        jwtTokenEntityService.saveJwtToken(jwtTokenEntity);
        jwtTokenEntityService.saveRefreshTokenToken(jwtTokenEntity);
        return tokenResponseVo;
    }
    private Claims getClaims(HttpServletRequest request) throws JwtTokenException {
        final String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isBlank(requestTokenHeader)) {
            throw new JwtTokenException("Token cannot be empty.", null);
        }
        //解析token
        String jwtToken = requestTokenHeader.substring(AUTHORIZATION_HEADER_START_STR_LENGTH);
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);
        JwtTokenEntity entity = jwtTokenEntityService.getJwtTokenEntityById(claims.getId());
        if (entity == null) {
            throw new JwtTokenException("Token is invalid or expired.", null);
        }

        return claims;

    }

}
