package com.professionalstrangers.error;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

/**
 * Controls what we send to through our REST service when an exception occurs.
 *
 * @see org.springframework.boot.web.servlet.error.DefaultErrorAttributes
 */
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    /**
     * Timestamp is converted to human readable format and exception is removed from map if exists.
     *
     * @param webRequest WebRequest.
     * @param includeStackTrace boolean.
     */
    @Override
    public Map<String, Object> getErrorAttributes(
            WebRequest webRequest,
            boolean includeStackTrace) {

        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String newDate = dateFormat.format((Date) errorAttributes.get("timestamp"));
        errorAttributes.put("timestamp", newDate);

        Object exception = errorAttributes.get("exception");
        if (exception != null) {
            errorAttributes.remove("exception");
        }

        return errorAttributes;
    }
}
