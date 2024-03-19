package com.nebulashrine.thparty.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class IpUtils {
    public String getRealIP(HttpServletRequest httpServletRequest){
        String ip = httpServletRequest.getHeader("X-Real-IP");
        if ("".equals(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = httpServletRequest.getHeader("Proxy-Client-IP");
        }
        if ("".equals(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
        }
        if ("".equals(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = httpServletRequest.getHeader("x-forwarded-for");
        }
        if ("".equals(ip) || "unknown".equalsIgnoreCase(ip)){
            ip = httpServletRequest.getRemoteAddr();
        }
        return ip;
    }
}
