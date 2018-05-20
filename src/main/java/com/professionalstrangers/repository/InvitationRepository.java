package com.professionalstrangers.repository;

import com.professionalstrangers.domain.Invitation;
import com.professionalstrangers.domain.Hotel;
import com.professionalstrangers.domain.User;
import com.professionalstrangers.domain.enums.InvitationStatus;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface InvitationRepository extends PagingAndSortingRepository<Invitation, Long> {

    List<Invitation> findAllByInvitationStatusIsNot(InvitationStatus invitationStatus);

    Page<Invitation> findAllByInvitationStatusIsNot(Pageable pageable, InvitationStatus invitationStatus);

    Invitation getById(Long id);

    List<Invitation> findAllByHotelsAndInvitationStatusIsNot(List<Hotel> hotels, InvitationStatus invitationStatus);

    List<Invitation> findAllByUser(User user);

    @Modifying
    @Query("delete from Invitation a where a.preferredStart <= ?1")
    void deleteAllExpiredSince(Date now);
}
