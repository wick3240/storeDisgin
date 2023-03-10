package com.wick.store.util;

import com.wick.store.domain.vo.LoginInfoVo;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Data
public class CustomUserDetails extends User {
    private LoginInfoVo loginInfoVo;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUserDetails(LoginInfoVo loginInfoVo,
                             String username,
                             String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.loginInfoVo = loginInfoVo;
    }
}
