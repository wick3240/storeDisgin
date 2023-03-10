package com.wick.store.util;

import com.wick.store.config.MpSsoConfig;

import com.wick.store.exception.JwtTokenException;
import io.jsonwebtoken.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author liyong
 */
@Component
public class JwtTokenUtil implements Serializable {
    @Autowired
    private MpSsoConfig config;


    public Claims getAllClaimsFromToken(String token) throws JwtTokenException {
        try {
            return Jwts.parser().setSigningKey(config.getJwtSecret()).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtTokenException("JWT Token has expired", e);
        } catch (UnsupportedJwtException
                | MalformedJwtException
                | IllegalArgumentException
                | SignatureException e) {
            throw new JwtTokenException("JWT Token is invalid.", e);
        }
    }


    public String generateToken(UserDetails userDetails, Map<String, Object> claims, String id) {
        return doGenerateToken(claims, userDetails.getUsername(), id);
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, String id) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(DateTime.now().plusSeconds(config.getJwtTokenValidity()).toDate())
                .setId(id)
                .signWith(SignatureAlgorithm.HS512, config.getJwtSecret())
                .compact();
    }

    public String createToken(Map<String, Object> claims, String subject, String id) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(DateTime.now().plusSeconds(config.getJwtTokenValidity()).toDate())
                .setId(id)
                .signWith(SignatureAlgorithm.HS512, config.getJwtSecret())
                .compact();
    }
}
