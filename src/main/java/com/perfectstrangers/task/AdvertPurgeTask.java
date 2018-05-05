package com.perfectstrangers.task;

import com.perfectstrangers.repository.AdvertRepository;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Scheduled check for any advers that are old. Deletes old adverts.
 */
@Service
@Transactional
public class AdvertPurgeTask {

    private AdvertRepository advertRepository;

    @Autowired
    public AdvertPurgeTask(AdvertRepository advertRepository) {
        this.advertRepository = advertRepository;
    }

    @Scheduled(cron = "${purge.everyDay}")
    public void purgeExpired() {
        Date now = Date.from(Instant.now());
        advertRepository.deleteAllExpiredSince(now);
    }
}
