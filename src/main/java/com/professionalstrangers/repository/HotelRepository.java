package com.professionalstrangers.repository;

import com.professionalstrangers.domain.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends PagingAndSortingRepository<Hotel, Long> {

    List<Hotel> findAll();

    Page<Hotel> findAll(Pageable pageable);

    Hotel findByName(String name);

    Hotel getById(Long id);
}
