package com.professionalstrangers.task;

import com.professionalstrangers.repository.InvitationRepository;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Scheduled check for any invitations that are old. Deletes old invitations.
 */
@Service
@Transactional
public class InvitationPurgeTask {

    private InvitationRepository invitationRepository;

    @Autowired
    public InvitationPurgeTask(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }

    @Scheduled(cron = "${purge.everyDay}")
    public void purgeExpired() {
        Date now = Date.from(Instant.now());
        invitationRepository.deleteAllExpiredSince(now);
    }
}
