package com.wick.store.eums;

public class AppConstant {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_HEADER_START_STR = "Bearer ";
    public static final int AUTHORIZATION_HEADER_START_STR_LENGTH = AUTHORIZATION_HEADER_START_STR.length();

    public static final String JWT_CLAIM_NAME_LOGIN_TYPE = "loginType";
    public static final String CACHE_REFRESH_TOKEN = "refresh-token";
    public static final String CACHE_JWT_TOKEN = "jwt-token";
    private AppConstant(){

    }
}
