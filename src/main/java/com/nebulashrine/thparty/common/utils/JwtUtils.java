package com.nebulashrine.thparty.common.utils;


import com.nebulashrine.thparty.entity.dto.UserDTO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Json Web Token工具类
 * @author Midsummra
 * @version 1.0.0
 */
@Component
public class JwtUtils {

    //    token密钥
    @Value("${jwt.secret}")
    private String secret;

    //    token头
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    //    过期时间
    @Value("${jwt.expiration}")
    private long expiration;

    private final String CLAIM_KEY_CREATED = "created";

    private final String CLAIM_KEY_UUID = "sub";


    /**
     * 生成token过期时间
     * @return token过期时间
     */
    private Date generateExpirationDate(){
        return new Date(System.currentTimeMillis()+expiration*1000);
    }

    /**
     * 生成token
     * @param claim 负载
     * @return token字符串
     */
    public String generateToken(Map<String, Object> claim){
        claim.put(CLAIM_KEY_CREATED, new Date());
        return Jwts.builder()
                .setClaims(claim)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 生成token
     * @param userDTO 用户ID
     * @return token字符串
     */
    public String generateToken(UserDTO userDTO){
        HashMap<String, Object> map = new HashMap<>();
        map.put(CLAIM_KEY_UUID,userDTO.getUsername());
        map.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(map);
    }

    /**
     * 获取token负载
     * @param token
     * @return token负载内容
     */
    public Claims getClaims(String token){
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e){
            claims = e.getClaims();
        }catch (Exception e){
            e.printStackTrace();
        }

        return claims;
    }

    /**
     * 从token中获取用户信息
     * @param token token
     * @return 用户信息
     */
    public String getUUIDFromToken(String token){
        String uuid = null;
        try {
            Claims claims = getClaims(token);
            uuid = (String) claims.get("sub");
        }catch (Exception e){
            JwtException jwtException = new JwtException("无法解析token或token已过期");
            jwtException.initCause(e);
            jwtException.printStackTrace();
        }
        return uuid;
    }

    public boolean validToken(String token, String username){
        if (username.equals("") || token == null){
            return false;
        }
        String uuid = getUUIDFromToken(token);
        String s = username;
        if (s != null && uuid != null){
            return uuid.equals(s);
        }
        return false;
    }

    public Date getExpirationDateFromToken(String token){
        Claims claims = getClaims(token);
        return claims.getExpiration();
    }

    public boolean isTokenExpired(String token){
        Date expiredTime = getExpirationDateFromToken(token);
        return expiredTime.before(new Date());
    }

    public String refreshToken(String oldToken){
        try{
            if ("".equals(oldToken)){
                return null;
            }
            Date expiredTime = getExpirationDateFromToken(oldToken);
            Claims claims = getClaims(oldToken);

            if (claims == null || expiredTime == null){
                return null;
            }

            if (expiredTime.before(new Date()) && (new Date().getTime() - expiredTime.getTime()) > 30*60){
                return oldToken;
            }
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
