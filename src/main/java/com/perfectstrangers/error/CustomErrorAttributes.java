package com.perfectstrangers.error;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(
            RequestAttributes requestAttributes,
            boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String newDate = dateFormat.format((Date) errorAttributes.get("timestamp"));
        errorAttributes.put("timestamp", newDate);
        return errorAttributes;
    }
}
