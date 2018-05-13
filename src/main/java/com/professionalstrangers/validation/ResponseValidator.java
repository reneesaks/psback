package com.professionalstrangers.validation;

import com.professionalstrangers.domain.Advert;
import com.professionalstrangers.domain.Response;
import com.professionalstrangers.domain.User;
import com.professionalstrangers.error.InvalidDateException;
import com.professionalstrangers.error.ResponseLimitException;
import com.professionalstrangers.error.ResponseTimeException;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks if a response is valid.
 */
public class ResponseValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseValidator.class);
    private static User user;
    private static Advert advert;
    private static Date preferredStart;
    private static Date preferredEnd;
    private static Date proposedTime;

    public ResponseValidator() {

    }

    /**
     * Check if proposed time is valid.
     *
     * @return boolean.
     */
    private static boolean isProposedTimeValid() {
        return proposedTime.after(preferredStart) && proposedTime.before(preferredEnd);
    }

    /**
     * Check if response limit has exceeded for user.
     *
     * @return boolean.
     */
    private static boolean isResponseLimitExceeded() {

        List<Response> advertResponses = advert.getResponses();

        for (Response response : advertResponses) {
            if (Objects.equals(response.getUser().getId(), user.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate response time.
     *
     * @param user user of the response.
     * @param advert existing advert.
     * @param response new response to that advert.
     * @throws ResponseLimitException when user has exceeded response limit.
     * @throws ResponseTimeException when response time is invalid.
     */
    public static void validate(User user, Advert advert, Response response) throws
            ResponseLimitException, ResponseTimeException {

        ResponseValidator.user = user;
        ResponseValidator.advert = advert;

        try {
            ResponseValidator.preferredStart = Date.from(Instant.parse(advert.getPreferredStart()));
            ResponseValidator.preferredEnd = Date.from(Instant.parse(advert.getPreferredEnd()));
            ResponseValidator.proposedTime = Date.from(Instant.parse(response.getProposedTime()));
        } catch (DateTimeParseException e) {
            LOGGER.error(e.getMessage());
            throw new InvalidDateException(e.getMessage());
        }

        if (isResponseLimitExceeded()) {
            throw new ResponseLimitException();
        }

        if (!isProposedTimeValid()) {
            throw new ResponseTimeException();
        }
    }
}
