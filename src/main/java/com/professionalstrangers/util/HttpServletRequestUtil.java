package com.professionalstrangers.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Helper class for HttpServletRequestUtil to get the remote address.
 */
public class HttpServletRequestUtil {

    /**
     * Extra logic to identify the original IP address of the client. In most cases, that’s not going to be
     * necessary, but in some network scenarios it is. For these rare scenarios, we’re using the
     * X-Forwarded-For header to get to the original IP.
     *
     * @param httpServletRequest HttpServletRequest
     * @return IP address
     */
    public String getRemoteAddress(HttpServletRequest httpServletRequest) {
        String xfHeader = httpServletRequest.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return httpServletRequest.getRemoteAddr();
        } else {
            return xfHeader.split(",")[0];
        }
    }
}
