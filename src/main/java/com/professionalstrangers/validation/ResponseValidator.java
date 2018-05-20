package com.professionalstrangers.validation;

import com.professionalstrangers.domain.Invitation;
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
    private static Invitation invitation;
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
        return (proposedTime.after(preferredStart) && proposedTime.before(preferredEnd)) ||
                proposedTime.equals(preferredStart) || proposedTime.equals(preferredEnd);
    }

    /**
     * Check if response limit has exceeded for user.
     *
     * @return boolean.
     */
    private static boolean isResponseLimitExceeded() {

        List<Response> invitationResponses = invitation.getResponses();

        for (Response response : invitationResponses) {
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
     * @param invitation existing invitation.
     * @param response new response to that invitation.
     * @throws ResponseLimitException when user has exceeded response limit.
     * @throws ResponseTimeException when response time is invalid.
     */
    public static void validate(User user, Invitation invitation, Response response) throws
            ResponseLimitException, ResponseTimeException {

        ResponseValidator.user = user;
        ResponseValidator.invitation = invitation;

        try {
            ResponseValidator.preferredStart = Date.from(Instant.parse(invitation.getPreferredStart()));
            ResponseValidator.preferredEnd = Date.from(Instant.parse(invitation.getPreferredEnd()));
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
