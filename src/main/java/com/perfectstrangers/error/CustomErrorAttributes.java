package com.perfectstrangers.error;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

/**
 * Controls what we send to through our REST service when an exception occurs.
 *
 * @see org.springframework.boot.autoconfigure.web.DefaultErrorAttributes
 */
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    /**
     * Timestamp is converted to human readable format and exception is removed from map if exists.
     *
     * @param requestAttributes RequestAttributes.
     * @param includeStackTrace boolean.
     */
    @Override
    public Map<String, Object> getErrorAttributes(
            RequestAttributes requestAttributes,
            boolean includeStackTrace) {

        Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
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
