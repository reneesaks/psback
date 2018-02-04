package com.perfectstrangers.task;

import com.perfectstrangers.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Service
@Transactional
public class TokensPurgeTask {

    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public TokensPurgeTask(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {

        Date now = Date.from(Instant.now());

        verificationTokenRepository.deleteAllExpiredSince(now);

    }

}
