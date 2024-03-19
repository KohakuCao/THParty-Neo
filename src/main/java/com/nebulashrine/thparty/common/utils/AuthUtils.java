package com.nebulashrine.thparty.common.utils;

import com.nebulashrine.thparty.common.exceptions.JwtAuthException;
import com.nebulashrine.thparty.entity.dto.UserDTO;
import com.nebulashrine.thparty.entity.mysqlEntity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class AuthUtils {

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    private final JwtUtils jwtUtils;

    @Autowired
    public AuthUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     *
     * 用给定的 User 生成 token，并且将用户权限写入 SecurityContextHolder
     * @param user User对象
     * @return 生成的token
     * @see User
     * @see UserDTO
     * @see JwtUtils
     * @see SecurityContextHolder
     */
    public String generateToken(@NotNull User user){
        UserDTO userDetails = new UserDTO(user);
        String token = jwtUtils.generateToken(userDetails);

        /*
        > We start by creating an empty SecurityContext.
        > You should create a new SecurityContext instance instead of using
        > SecurityContextHolder.getContext().setAuthentication(authentication)
        > to avoid race conditions across multiple threads.
        > 防止线程的竞态条件产生的潜在问题
        > https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html
         */
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        emptyContext.setAuthentication(usernamePasswordAuthenticationToken);
        SecurityContextHolder.setContext(emptyContext);

        return token;
    }

    public void addToken(HttpServletResponse response, String token) throws JwtAuthException {
        Cookie cookie = new Cookie(tokenHeader, token);
        cookie.setPath("/");
        cookie.setMaxAge((int) expiration);
        try {
            response.addCookie(cookie);
        }catch (Exception e){
            throw new JwtAuthException("cookie分发异常");
        }
    }

    public void deleteToken(HttpServletResponse response) throws JwtAuthException{
        SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(null, null, null);
        emptyContext.setAuthentication(usernamePasswordAuthenticationToken);
        SecurityContextHolder.setContext(emptyContext);

        Cookie cookie = new Cookie(tokenHeader, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        try {
            response.addCookie(cookie);
        }catch (Exception e){
            throw new JwtAuthException("cookie清除异常");
        }

    }

    public String getTokenFromRequest(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (ObjectUtils.isEmpty(cookies)){
            return null;
        }
        String token = null;
        for (Cookie cookie : cookies) {
            if (tokenHeader.equals(cookie.getName())){
                token = cookie.getValue();
                break;
            }
        }
        boolean expired = jwtUtils.isTokenExpired(token);
        return expired ? null : token;
    }

    public String getUUIDFromToken(String token){
        if (token == null){
            return null;
        }

        String uuid = jwtUtils.getUUIDFromToken(token);
        return uuid;
    }

}
