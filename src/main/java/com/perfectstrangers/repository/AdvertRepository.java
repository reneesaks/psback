package com.perfectstrangers.repository;

import com.perfectstrangers.domain.Advert;
import com.perfectstrangers.domain.Hotel;
import com.perfectstrangers.domain.User;
import com.perfectstrangers.domain.enums.AdvertStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AdvertRepository extends PagingAndSortingRepository<Advert, Long> {

    List<Advert> findAllByAdvertStatusIsNot(AdvertStatus advertStatus);

    Page<Advert> findAll(Pageable pageable);

    Advert findById(Long id);

    List<Advert> findAllByHotels(List<Hotel> hotels);

    List<Advert> findAllByUser(User user);
}
