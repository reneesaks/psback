package com.perfectstrangers.validation;

import com.perfectstrangers.domain.Advert;
import com.perfectstrangers.domain.Response;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.error.ResponseLimitException;
import com.perfectstrangers.error.ResponseTimeException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Checks if a response is valid.
 */
public class ResponseValidator {

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
        ResponseValidator.preferredStart = Date.from(Instant.parse(advert.getPreferredStart()));
        ResponseValidator.preferredEnd = Date.from(Instant.parse(advert.getPreferredEnd()));
        ResponseValidator.proposedTime = Date.from(Instant.parse(response.getProposedTime()));

        if (isResponseLimitExceeded()) {
            throw new ResponseLimitException();
        }

        if (!isProposedTimeValid()) {
            throw new ResponseTimeException();
        }
    }
}
