package com.perfectstrangers.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {

    public String getRemoteAddress(HttpServletRequest httpServletRequest) {
        String xfHeader = httpServletRequest.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return httpServletRequest.getRemoteAddr();
        } else {
            return xfHeader.split(",")[0];
        }
    }
}
