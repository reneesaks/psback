package com.professionalstrangers.validation;

import com.professionalstrangers.domain.Invitation;
import com.professionalstrangers.error.InvitationTimeException;
import com.professionalstrangers.error.DailyInvitationLimitException;
import com.professionalstrangers.error.InvalidDateException;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Checks if an invitation is valid.
 */
@Component
public class InvitationValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationValidator.class);
    private static List<Invitation> invitations;
    private static Date preferredStart;
    private static Date preferredEnd;
    private static Date today;

    public InvitationValidator() {
    }

    /**
     * Check if daily limit is exceeded.
     *
     * @return boolean.
     */
    private static boolean isDailyLimitExceeded() {

        int counter = 0;

        for (Invitation invitation : invitations) {

            Date invitationDate = Date.from(Instant.parse(invitation.getCreatedDate()));
            if (DateUtils.isSameDay(today, invitationDate)) {
                counter++;
            }
        }

        return counter >= 5;
    }

    /**
     * Check if invitation start and end times are valid.
     *
     * @return boolean.
     */
    private static boolean isTimeValid() {
        return DateUtils.addHours(preferredStart, 24).after(preferredEnd) &&
                preferredEnd.after(preferredStart) &&
                preferredStart.after(today);
    }

    /**
     * Validate invitation time.
     *
     * @param invitation new invitation.
     * @param invitations list of invitations belonging to the user.
     * @throws DailyInvitationLimitException when user has exceeded daily invitations limit.
     * @throws InvitationTimeException when invitation time is invalid.
     */
    public static void validate(Invitation invitation, List<Invitation> invitations) throws
            DailyInvitationLimitException,
            InvitationTimeException {
        InvitationValidator.invitations = invitations;

        try {
            InvitationValidator.preferredStart = Date.from(Instant.parse(invitation.getPreferredStart()));
            InvitationValidator.preferredEnd = Date.from(Instant.parse(invitation.getPreferredEnd()));
            InvitationValidator.today = Date.from(Instant.now());
        } catch (DateTimeParseException e) {
            LOGGER.error(e.getMessage());
            throw new InvalidDateException(e.getMessage());
        }

        if (isDailyLimitExceeded()) {
            throw new DailyInvitationLimitException();
        }

        if (!isTimeValid()) {
            throw new InvitationTimeException();
        }
    }
}
