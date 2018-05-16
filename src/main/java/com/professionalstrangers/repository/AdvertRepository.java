package com.professionalstrangers.repository;

import com.professionalstrangers.domain.Advert;
import com.professionalstrangers.domain.Hotel;
import com.professionalstrangers.domain.User;
import com.professionalstrangers.domain.enums.AdvertStatus;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdvertRepository extends PagingAndSortingRepository<Advert, Long> {

    List<Advert> findAllByAdvertStatusIsNot(AdvertStatus advertStatus);

    Page<Advert> findAllByAdvertStatusIsNot(Pageable pageable, AdvertStatus advertStatus);

    Advert getById(Long id);

    List<Advert> findAllByHotelsAndAdvertStatusIsNot(List<Hotel> hotels, AdvertStatus advertStatus);

    List<Advert> findAllByUser(User user);

    @Modifying
    @Query("delete from Advert a where a.preferredStart <= ?1")
    void deleteAllExpiredSince(Date now);
}