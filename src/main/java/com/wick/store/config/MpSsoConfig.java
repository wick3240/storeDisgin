package com.wick.store.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wick.store.eums.AccountType;
import lombok.Data;
import org.apache.tomcat.util.collections.CaseInsensitiveKeyMap;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author liyong
 */
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "mp-sso")
public class MpSsoConfig {
    private String defaultBu;
    private String jwtSecret;
    private Integer jwtTokenValidity;
    private Integer refreshTokenValidity;
    private Integer samlMaxAuthenticationAge;
    private String samlEntityId;
    private String samlEntityBaseUrl;
    private String samlKeystoreLocation;
    private String samlKeystorePassword;
    private String samlKeystoreAlias;
    private List<BuSsoInfo> buSsoInfos;
    private List<IdpInfo> idpInfos;
    private BuSsoInfo defaultBuSsoInfo;
    private Map<String, BuSsoInfo> buSsoInfoMap;
    private Map<String, IdpInfo> idpInfoNameMap;

    @JsonIgnore
    public BuSsoInfo getDefaultBuSsoInfo() {
        if (defaultBuSsoInfo == null) {
            defaultBuSsoInfo = getBuSsoInfoByBu(defaultBu);
        }
        return defaultBuSsoInfo;
    }

    @JsonIgnore
    public BuSsoInfo getBuSsoInfoByBu(String bu) {
        if (buSsoInfoMap == null) {
            buSsoInfoMap = buSsoInfos.stream().collect(
                    Collectors.toMap(BuSsoInfo::getName, Function.identity(), (o1, o2) -> o2,
                            CaseInsensitiveKeyMap::new));
        }
        return buSsoInfoMap.get(bu);
    }

    @JsonIgnore
    public IdpInfo getIdpInfoByLoginType(AccountType loginType) {
        if (idpInfoNameMap == null) {
            idpInfoNameMap = idpInfos.stream().collect(
                    Collectors.toMap(k -> k.getIdpName().name(), Function.identity(), (o1, o2) -> o2,
                            CaseInsensitiveKeyMap::new));
        }
        return idpInfoNameMap.get(loginType.name());
    }

    @Data
    public static class IdpInfo {
        private String idp;
        private AccountType idpName;
        private String metadataLocation;
    }

    @Data
    public static class BuSsoInfo {
        private String name;
        private List<String> validateCodeUris;
        private String loginFailureUri;
    }
}
