package com.perfectstrangers.validation;

import com.perfectstrangers.domain.Advert;
import com.perfectstrangers.error.DailyAdvertLimitExceeded;
import com.perfectstrangers.error.EntityNotFoundException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Checks if advert is valid.
 */
@Component
public class AdvertValidator {

    private static Advert advert;
    private static List<Advert> adverts;

    public AdvertValidator() {
    }

    /**
     * Check if daily limit is exceeded
     *
     * @return boolean
     */
    private static boolean isDailyLimitExceeded() {

        int counter = 0;
        Date today = Date.from(Instant.now());

        for (Advert advert: adverts) {

            Date advertDate = Date.from(Instant.parse(advert.getCreatedDate()));
            if (org.apache.commons.lang3.time.DateUtils.isSameDay(today, advertDate)) {
                counter++;
            }
        }

        return counter >= 5;
    }

    public static void validate(Advert advert, List<Advert> adverts) throws
            EntityNotFoundException,
            DailyAdvertLimitExceeded {
        AdvertValidator.advert = advert;
        AdvertValidator.adverts = adverts;

        if (isDailyLimitExceeded()) {
            throw new DailyAdvertLimitExceeded();
        }
    }
}
