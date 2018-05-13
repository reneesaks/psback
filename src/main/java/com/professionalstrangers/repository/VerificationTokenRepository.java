package com.professionalstrangers.repository;

import com.professionalstrangers.domain.User;
import com.professionalstrangers.domain.VerificationToken;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {

    List<VerificationToken> findAll();

    VerificationToken getById(Long id);

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

    @Modifying
    @Query("delete from VerificationToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);
}
