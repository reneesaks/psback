package com.perfectstrangers.validation;

import com.perfectstrangers.domain.Advert;
import com.perfectstrangers.error.AdvertStartAndEndException;
import com.perfectstrangers.error.DailyAdvertLimitException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

/**
 * Checks if advert is valid.
 */
@Component
public class AdvertValidator {

    private static Advert advert;
    private static List<Advert> adverts;
    private static Date preferredStart;
    private static Date preferredEnd;

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

        for (Advert advert : adverts) {

            Date advertDate = Date.from(Instant.parse(advert.getCreatedDate()));
            if (DateUtils.isSameDay(today, advertDate)) {
                counter++;
            }
        }

        return counter >= 5;
    }

    /**
     * Check if start and end time are on the same day
     *
     * @return boolean
     */
    private static boolean areOnTheSameDay() {
        return DateUtils.isSameDay(preferredStart, preferredEnd);
    }

    private static boolean isEndGreater() {
        return preferredEnd.after(preferredStart);
    }

    public static void validate(Advert advert, List<Advert> adverts) throws
            DailyAdvertLimitException,
            AdvertStartAndEndException {
        AdvertValidator.advert = advert;
        AdvertValidator.adverts = adverts;
        AdvertValidator.preferredStart = Date.from(Instant.parse(advert.getPreferredStart()));
        AdvertValidator.preferredEnd = Date.from(Instant.parse(advert.getPreferredEnd()));

        if (isDailyLimitExceeded()) {
            throw new DailyAdvertLimitException();
        }

        if (!areOnTheSameDay()) {
            throw new AdvertStartAndEndException();
        }

        if (!isEndGreater()) {
            throw new AdvertStartAndEndException();
        }
    }
}
