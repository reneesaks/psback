package com.perfectstrangers.validation;

import com.perfectstrangers.domain.Advert;
import com.perfectstrangers.error.AdvertTimeException;
import com.perfectstrangers.error.DailyAdvertLimitException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

/**
 * Checks if an advert is valid.
 */
@Component
public class AdvertValidator {

    private static List<Advert> adverts;
    private static Date preferredStart;
    private static Date preferredEnd;
    private static Date today;

    public AdvertValidator() {
    }

    /**
     * Check if daily limit is exceeded.
     *
     * @return boolean.
     */
    private static boolean isDailyLimitExceeded() {

        int counter = 0;

        for (Advert advert : adverts) {

            Date advertDate = Date.from(Instant.parse(advert.getCreatedDate()));
            if (DateUtils.isSameDay(today, advertDate)) {
                counter++;
            }
        }

        return counter >= 5;
    }

    /**
     * Check if advert start and end times are valid.
     *
     * @return boolean.
     */
    private static boolean isTimeValid() {
        return DateUtils.isSameDay(preferredStart, preferredEnd) &&
                preferredEnd.after(preferredStart) &&
                preferredStart.after(today);
    }

    /**
     * Validate advert time.
     *
     * @param advert new advert.
     * @param adverts list of adverts belonging to the user.
     * @throws DailyAdvertLimitException when user has exceeded daily adverts limit.
     * @throws AdvertTimeException when advert time is invalid.
     */
    public static void validate(Advert advert, List<Advert> adverts) throws
            DailyAdvertLimitException,
            AdvertTimeException {
        AdvertValidator.adverts = adverts;
        AdvertValidator.preferredStart = Date.from(Instant.parse(advert.getPreferredStart()));
        AdvertValidator.preferredEnd = Date.from(Instant.parse(advert.getPreferredEnd()));
        AdvertValidator.today = Date.from(Instant.now());

        if (isDailyLimitExceeded()) {
            throw new DailyAdvertLimitException();
        }

        if (!isTimeValid()) {
            throw new AdvertTimeException();
        }
    }
}
