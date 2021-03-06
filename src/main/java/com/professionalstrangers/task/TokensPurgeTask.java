package com.professionalstrangers.task;

import com.professionalstrangers.repository.PasswordResetTokenRepository;
import com.professionalstrangers.repository.VerificationTokenRepository;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Scheduled check for any tokens that have expired. Deletes expired tokens.
 */
@Service
@Transactional
public class TokensPurgeTask {

    private VerificationTokenRepository verificationTokenRepository;
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public TokensPurgeTask(
            VerificationTokenRepository verificationTokenRepository,
            PasswordResetTokenRepository passwordResetTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Scheduled(cron = "${purge.everyDay}")
    public void purgeExpired() {
        Date now = Date.from(Instant.now());
        verificationTokenRepository.deleteAllExpiredSince(now);
        passwordResetTokenRepository.deleteAllExpiredSince(now);
    }
}
