package com.perfectstrangers.repository;

import com.perfectstrangers.domain.Advert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AdvertRepository extends PagingAndSortingRepository<Advert, Long> {

    List<Advert> findAll();

    Page<Advert> findAll(Pageable pageable);

    Advert findById(Long id);

}
